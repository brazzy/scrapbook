function Point(x, y){
	this.x = x;
	this.y = y;
}

function Dimension(width, height){
	this.width = width;
	this.height = height;
}

function Mandelbrot(display, control, colorNum){

	this.canvas = display;
	this.context = display.getContext("2d");
	this.palette = new Array();
	var colorNum = colorNum || 256;

    // Initializes the variables in a way such that a rectangle in the complex
    // plane with its upper left corner at (-2,1.5) and a width and height of 3
    // fits completely into the applet, no matter what the Applet's aspect ratio
    // is, and the entire Apfelmännchen can be seen.
    if(this.canvas.width > this.canvas.height){
      var diff = ((this.canvas.width-this.canvas.height)*3)/(2*this.canvas.height);
      this.initVars(new Point(-diff-2.0, 1.5), this.canvas.width*3/this.canvas.height);
    } else {
      var diff = ((this.this.canvas.height-this.canvas.width)*3)/(2*this.canvas.width);
      this.initVars(new Point(-2.0, diff+1.5), 3.0);
    }
	
    // Create a nice-looking palette by having the red, blue and green
    // part follow sine, cosine and inverted cosine curves.
	this.palette = new Array();
	
    for(var i=0; i<colorNum; i++){
      var red  = Math.floor((Math.sin(Math.PI*2/colorNum*i)+1)*128*0.999);
      var blue = Math.floor((Math.cos(Math.PI*2/colorNum*i)+1)*128*0.999);
      var green= Math.floor((1-Math.cos(Math.PI*2/colorNum*i))*128*0.999);
      this.palette.push(new Array(red,green,blue));
    }

    // prepare user interaction
    
    var ctrl = new MandelbrotControl();
    ctrl.init(this, control);
}

/**
* Initializes the important variables in the beginning and whenever
* the user has zoomed in or out.
*
* @param Point leftUpper in the complex plane
* @param number size in the complex plane
*/
Mandelbrot.prototype.initVars = function(leftUpper, size){
	this.painting = false;
	this.leftUpper=leftUpper;
	this.size=size;
	this.currentComplex = new Point(this.leftUpper.x, this.leftUpper.y);
	this.step = this.size/this.canvas.width;
	this.currentPixel = new Point(0, 0);
    this.image = this.context.createImageData(this.canvas.width,this.canvas.height);
	this.stepping = 10;
	this.nextStep = 10;
    this.steppingTime = new Date().getTime()

	// A rule-of-thumb approximation to get a sufficient iteration threshold
	// relative to the resolution.
	this.threshold = Math.ceil(20+100*(2-(Math.log(this.step*500)/Math.log(5))));	
}


Mandelbrot.prototype.paint = function(){
    var display = this;

    var compute = function(){
        while(display.painting){
        	// The actual computation to decide whether the current pixel is
            // inside or outside the Mandelbrot set.
            var inside = false;
            var outside = false;
            var iterations = 0;
            var iterationX = 0;
            var iterationY = 0;
            
            do{
              iterations++;
              var xSquared = iterationX*iterationX;
              var ySquared = iterationY*iterationY;
              iterationY = 2*iterationX*iterationY+display.currentComplex.y;
              iterationX = xSquared - ySquared + display.currentComplex.x;
              inside = iterations>display.threshold; // pixel is considered part of the M set.
              outside = xSquared+ySquared > 4; // pixel is not part of the M set.
            } while (!inside && !outside); // iterate until decision is made

            // draw pixel
            var offset = (display.currentPixel.y*display.canvas.width + display.currentPixel.x)*4
            if(inside){
            	display.image.data[offset+0] = 0;
            	display.image.data[offset+1] = 0;
            	display.image.data[offset+2] = 0;
            	display.image.data[offset+3] = 255;
            } else {
            	var color = display.palette[iterations%display.palette.length]
            	display.image.data[offset+0] = color[0];
            	display.image.data[offset+1] = color[1];
            	display.image.data[offset+2] = color[2];
            	display.image.data[offset+3] = 255;
            }

            display.currentComplex.y -= display.step; // increase y coordinate
            if(++display.currentPixel.y<display.canvas.height){
              continue; // advance to vertically next pixel
            }

            // If this is reached, the current x column is finished.
            display.currentPixel.y=0;
            display.currentComplex.y = display.leftUpper.y; // reset y coordinate;
            display.currentComplex.x += display.step; // increase x coordinate
            if(++display.currentPixel.x < display.canvas.width){
              if(display.currentPixel.x == display.nextStep){
            	  display.context.putImageData(display.image, 0, 0);
            	  window.setTimeout(compute, 0);
            	  
              	  // balacing repaint overhead and display of progress
            	  var timeDiff = new Date().getTime() - display.steppingTime;            	  
            	  display.stepping = Math.ceil(display.stepping * (50 / timeDiff));
            	  display.nextStep += display.stepping;
            	  display.steppingTime = new Date().getTime();
            	  return;
              }
              continue; // advance to next x column.
            }

            // If this is reached, the image is complete and computation can be
            // paused until a zoom request happens.
            display.context.putImageData(display.image, 0, 0);
            display.painting = false;
        }
    }
    
    if(!display.painting){
    	display.painting = true;
        compute.call();
    }
    
}

function MandelbrotControl(){
	this.zooming = false;
	this.zoomout = false;
	this.firstClick = new Point();
	this.context = null;
}

function getMousePos(canvas, evt) {
    var rect = canvas.getBoundingClientRect();
    return new Point(evt.clientX - rect.left, evt.clientY - rect.top);
}

MandelbrotControl.prototype.init = function(display, controlCanvas){
	this.context = controlCanvas.getContext("2d");
	var ctrl = this;

    /** Mouse click prepares and performs a zoom. */
	controlCanvas.onclick = function(e){
	  var coords = getMousePos(controlCanvas, e)
      if(!ctrl.zooming){ // prepare zoom
        ctrl.zooming = true;
        ctrl.firstClick = new Point(coords.x, coords.y);
        ctrl.zoomout = e.shiftKey;
      } else { // perform zoom
        ctrl.zooming = false;
        ctrl.context.clearRect(0, 0, controlCanvas.width, controlCanvas.height);

        var clickDiffX = Math.abs(ctrl.firstClick.x-coords.x);
        var clickDiffY = Math.abs(ctrl.firstClick.y-coords.y);

        // Compute the width of the rectangle, considering that it should have
        // the same aspect ratio as the applet, thus we need to constrain the
        // rectangle.
        var zoomRectWidth = Math.min(clickDiffX, clickDiffY*display.canvas.width/display.canvas.height);
        display.done = false;
        var newSize;        
        var newLeftUpper;
          
        if(!ctrl.zoomout){ // zoom in
			// New (after the zoom) value of {@link #size}.
			newSize = display.size * zoomRectWidth / display.canvas.width;
			newLeftUpper = new Point(
			  display.leftUpper.x+(display.size*ctrl.firstClick.x/display.canvas.width),
			  display.leftUpper.y-(display.size*ctrl.firstClick.y/display.canvas.width)
			);
        } else { // zoom out
	        newSize = display.size / zoomRectWidth * this.width;
	        newLeftUpper = new Point(
	          display.leftUpper.x-(newSize*ctrl.firstClick.x/display.canvas.width),
	          display.leftUpper.y+(newSize*ctrl.firstClick.y/display.canvas.width)
	        );
	    }
        
	    display.initVars(newLeftUpper, newSize);
	    display.paint();
      }
    }
	
	controlCanvas.onmousemove = function(e){
	  var coords = getMousePos(controlCanvas, e)

      if(ctrl.zooming){ // only draw while zoom is being prepared.
          ctrl.context.clearRect(0, 0, controlCanvas.width, controlCanvas.height);
          ctrl.context.strokeStyle="#FFFFFF";
	      var clickDiffX = Math.abs(ctrl.firstClick.x-coords.x);
	      var clickDiffY = Math.abs(ctrl.firstClick.y-coords.y);

          // Compute the width of the rectangle, considering that it should have
          // the same aspect ratio as the applet, thus we need to constrain the
          // rectangle.
          var zoomRectWidth = Math.min(clickDiffX, clickDiffY*display.canvas.width/display.canvas.height);
          // draw rectangle
          ctrl.context.strokeRect(ctrl.firstClick.x, ctrl.firstClick.y, zoomRectWidth, display.canvas.height*zoomRectWidth/display.canvas.width);
      }
    }
}


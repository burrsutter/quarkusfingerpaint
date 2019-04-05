function drawSmile(aCanvas) {
  // logic from 
  // http://curran.github.io/HTML5Examples/canvas/smileyFace.html
  var context = aCanvas.getContext('2d');
  var centerX = aCanvas.width / 2;
  var centerY = aCanvas.height / 2;
  var radius = 50;
  var eyeRadius = 6;
  var eyeXOffset = 20;
  var eyeYOffset = 20;

  // draw the yellow circle
  context.beginPath();
  context.arc(centerX, centerY, radius, 0, 2 * Math.PI, false);
  context.fillStyle = 'yellow';
  context.fill();
  context.lineWidth = 5;
  context.strokeStyle = 'black';
  context.stroke();
  
  // draw the eyes
  context.beginPath();
  var eyeX = centerX - eyeXOffset;
  var eyeY = centerY - eyeXOffset;
  context.arc(eyeX, eyeY, eyeRadius, 0, 2 * Math.PI, false);
  var eyeX = centerX + eyeXOffset;
  context.arc(eyeX, eyeY, eyeRadius, 0, 2 * Math.PI, false);
  context.fillStyle = 'black';
  context.fill();

  // draw the mouth
  context.beginPath();
  context.arc(centerX, centerY, 25, 0, Math.PI, false);
  context.stroke();    
} // drawSmile

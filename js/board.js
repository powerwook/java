window.onload =  setup();
var pointA;
var pointB;
var totalVotes = pointA + pointB;
function addleft(){
    pointB += 1;
    totalVotes += 1;
    updatePoints();
    console.log(pointA + ' '+ pointB);
}
function addright(){
    pointA += 1;
    totalVotes += 1;
    updatePoints();
    console.log(pointA + ' '+ pointB);
}
function updatePoints(){
    var percentA = (pointA / totalVotes) * 100;
    var percentB = (pointB / totalVotes) * 100;
    var size = percentA + "% " + percentB + "%";
    document.getElementById("size-one").innerHTML = Math.round(percentB) + '%';
    document.getElementById("size-two").innerHTML = Math.round(percentA) + '%';
    document.getElementById("voting-box").style.gridTemplateColumns=  percentA + "% " + percentB + "%";
    document.getElementById("total-votes").innerHTML = "Total Votes Casted: " + totalVotes;
    document.getElementById("total-left").innerHTML = "Option B: " + pointB;
    document.getElementById("total-right").innerHTML = "Option A: " + pointA;
    document.getElementById("host-name").innerHTML = "Hosted by: pragyakar";
}
function setup() {
    pointA = 1;
    pointB = 1;
    totalVotes = pointA + pointB;
    document.getElementById("option-one").innerHTML = "Option B";
    document.getElementById("option-two").innerHTML = "Option A";
    updatePoints();
}

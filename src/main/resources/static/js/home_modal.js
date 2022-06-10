window.onload=function(){
const modalButton = document.getElementById("modal_button");
const navBar = document.getElementById("nav_bar");
const modalLayout = document.getElementById("modal_layout");
const homeLogoutButton =document.getElementById("home_logout");
console.log("hello");
modalButton.addEventListener("click", ()=>{
    navBar.classList.remove("modal-close");
    navBar.style.display="block";
    modalLayout.style.display="block";
    modalLayout.addEventListener("click", (e)=>{
        if(e.target.id==="modal_layout"){
            closeModal();
        }
    })
});
function closeModal(){
    navBar.classList.add("modal-close");
    modalLayout.style.display="none";
    
};
if(homeLogoutButton!==null){
homeLogoutButton.addEventListener("click",function(){
	
		if(confirm("로그아웃에 성공하셨습니다.")){
		window.location.href="/";
		}
})
}
}
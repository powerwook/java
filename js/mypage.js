window.onload=function(){
const mypageLogout = document.getElementById("mypage-logout");
const mypageMemberOut = document.getElementById("mypage_member_out");
const modalLayout = document.getElementById("modal_layout");
const mypageLogoutModal = document.getElementById("mypage_logout_modal");
const mypageMemberModal = document.getElementById("mypage_member_modal");
mypageLogout.addEventListener("click",()=>{
    modalLayout.style.display="block";
    mypageLogoutModal.style.display="block";
});

mypageMemberOut.addEventListener("click",()=>{
    modalLayout.style.display="block";
    mypageMemberModal.style.display="block";
});
}
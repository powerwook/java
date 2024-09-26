window.onload=function(){
const mypageLogout = document.getElementById("mypage_logout");
const mypageMemberOut = document.getElementById("mypage_member_out");
const modalLayout = document.getElementById("modal_layout");
const mypageLogoutModal = document.getElementById("mypage_logout_modal");
const mypageMemberModal = document.getElementById("mypage_member_modal");
const mypageModify = document.getElementById("mypage_edit_button");
const mypageNickname = document.getElementById("mypage_edit_nickname");
const mypagePencil = document.getElementById("mypage_pencil");
const mypageForm = document.getElementById("mypage_form");
const mypageLogoutButton = document.getElementById("mypage_logout_button");
const mypageCaution = document.getElementById("caution");
const mypageCommentFilter = document.getElementById("mypage_comment_filter");
const mypageFilterModal = document.getElementById("mypage_filter_modal");
const mypageSignoutButton = document.getElementById("mypage_signout_button");

	console.log(mypageSignoutButton);
if(mypageSignoutButton!=null){
	mypageSignoutButton.addEventListener("click",()=>{
		const is_checked = mypageCaution.checked;
		if(!is_checked){
			alert("주의사항을 체크해주세요.");
		}else{
			mypageSignoutButton.type="submit";	
		}
	})
}

//logout 모달
if(mypageLogout!==null){
	mypageLogout.addEventListener("click",()=>{
	    modalLayout.style.display="block";
	    mypageLogoutModal.style.display="block";
	});
}
//signout 모달
if(mypageMemberOut!==null){
	mypageMemberOut.addEventListener("click",()=>{
	    modalLayout.style.display="block";
	    mypageMemberModal.style.display="block";
	});
}
//filter 모달
if(mypageCommentFilter!==null){
	mypageCommentFilter.addEventListener("click",()=>{
	    modalLayout.style.display="block";
	    mypageFilterModal.style.display="block";
	});
}

if(mypageModify!==null){
	
	mypageModify.addEventListener("click", ()=>{
		if(mypagePencil.src==="https://cdn.poomang.com/icons/ico_edit_txt.svg"){
			mypagePencil.src="https://cdn.poomang.com/icons/ico_submit.svg";
			mypageNickname.focus();
			element_content=mypageNickname.value;
			mypageNickname.value='';
			mypageNickname.value=element_content;
		}else{
			mypagePencil.src="https://cdn.poomang.com/icons/ico_edit_txt.svg";
		}
	});
}
mypageLogoutButton.addEventListener("click",
	function()
	{
		if(confirm("로그아웃에 성공하셨습니다.")){
		window.location.href="/";
	}
});

// (1) 스토리 이미지 업로드를 위한 사진 선택 로직


}

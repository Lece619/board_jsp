/**
 * 회원가입
 */

$("#term").on("click",function(){
	if($(this).is(":checked")){
		$(".terms").prop("checked",true);
	}else{
		$(".terms").prop("checked",false);
	}
})

$(".term-detail a").on("click", function(e){
	e.preventDefault();//가지못하게 막음
	if($("#"+$(this).attr("href")).css("display")=="none"){
		$("#"+$(this).attr("href")).show();
		$(this).html("닫기");	
	}else{
		$("#"+$(this).attr("href")).hide();
		$(this).html("펼쳐보기");
	}
})

let check = false;

function formSubmit(){
	var form = document.joinForm;
	
	//유효성검사
	//값이 있으면 true 없으면 false
	//정규 표현식 .*?하나라도 
	let reg = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*-]).{8,}$/;
	let hangleCheck = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
	let engNum =  /^[a-zA-Z0-9]*$/;
	
	if(!form.memberId.value || !check){
		alert("아이디를 확인해주세요.");
		form.memberId.focus();
		return;
	}
	
	if(form.memberId.value.length < 6 || form.memberId.value.length > 16){
		alert("아이디는 6자 이상, 16자 이하로 입력해주세요.");
		form.memberId.focus();
		return;
	}
	
	if(hangleCheck.test(form.memberId.value)){
		alert("아이디에 한글을 사용할 수 없습니다.");
		form.memberId.focus();
		return;
	}
	
	if(!engNum.test(form.memberId.value)){
		alert("아이디는 영문과 숫자만 이용해주세요.");
		form.memberId.focus();
		return;
	}
	
	
///////////////
	if(!form.memberPw.value){
		alert("비밀번호를 입력해주세요");
		form.memberPw.focus();
		return;
	}
	
	if(form.memberPw.value.length < 4 || form.memberPw.value.length > 20){
		alert("비밀번호는 4자 이상, 20자 이하로 입력해주세요.");
		form.memberPw.focus();
		return;
	}
	
//	if(!reg.test(form.memberPw.value)){
//		alert("비밀번호는 8자리 이상이어야 하며, 대문자/소문자/숫자/특수문자 모두 포함해야 합니다.");
//		form.memberPw.focus();
//		return;
//	}
	
	//같은문자를 4번 사용 오류
	if(/(\w)\1\1\1/.test(form.memberPw.value)){
		alert("같은 문자를 4번 이상 사용하실 수 없습니다.");
		form.memberPw.focus();
		return;
	}
	
	//비밀번호 안에 아이디가 있을 때
	if((form.memberPw.value.search(form.memberId.value) != -1)){
		alert("비밀번호에 아이디를 포함할 수 없습니다.");
		form.memberPw.focus();
		return;
	}
	
	//비밀번호에 한글이 있으면 안된다form.password.value
	if(hangleCheck.test(form.memberPw.value)){
		alert("비밀번호에 한글을 사용할 수 없습니다.");
		form.memberPw.focus();
		return;
	}
	
	//비밀번호에 공백을 표현할 수 없다.
	if(form.memberPw.value.search(/\s/) != -1){ 
		alert("비밀번호에 공백은 사용할 수 없습니다.");
		form.memberPw.focus();
		return;
	}

	if(!form.memberName.value){
		alert("이름을 입력해주세요");
		form.memberName.focus();
		return;
	}
	
	if(!form.memberAge.value){
		alert("나이를 입력해주세요")
		form.memberAge.focus();
		return;
	}
	
	////
	check = false;
	
	$.each($(".terms"),function(index,item){
		if(!$(item).is(":checked")){
			check = true;
		}
	}) 
	if(check){
		alert("이용약관 동의가 필요합니다.")
		form.term.focus();
		return;
	}
	
	/////////////////////
	
	
	form.submit();
}

function checkId(id){
	check= false;
	if(id == ""){
		$("#idCheck_text").text("");
		return;
	}
	$.ajax({
		//jsp파일에서 contextPath 를 js로 선언해둔다.
		url: contextPath + "/member/MemberCheckIdOk.me?memberId="+id,
		type:"get",//get방식
		dataType:"json",//json타입. 전달을 받을 타입{status : ok} {status : not-ok} 둘중하나로 받아진다. 
		success:function(result){
			if(result.status == 'ok'){
				$("#idCheck_text").text("사용 가능");
				$("#idCheck_text").css("color","blue");
				check = true;
			}else{
				$("#idCheck_text").text("사용 불가");
				$("#idCheck_text").css("color","red");
			}
		},
		error:function(){
			console.log("오류");
		}
	});
}
//memberId input에 땔때마다 keyup 이벤트가 일어날 때마다 check 적용
// 인풋테그중 이름이 memberid에 keyup이 일어날 때마다
$("input[name='memberId']").keyup(function(){
	checkId($(this).val());
	alert(form.profileImg.files.length);
});





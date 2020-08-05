const acceptRequestEmployerRegister = (email) => {
	if (confirm("Hệ thống sẽ gửi email đến tài địa chỉ " + email + " cùng với tài khoản và mật khẩu ngẫu nhiên")) {
		alert("Đã gửi email");
	} else {
		alert("Đã hủy");
	}
}
if (alertMsg != null) {
    console.log("메시지>" + alertMsg.text);
  Swal.fire({
    icon: alertMsg.icon,
    title: alertMsg.title,
    text: alertMsg.text,

    showClass: {
      popup: null
    },
    hideClass: {
      popup: null
    }
  });
}

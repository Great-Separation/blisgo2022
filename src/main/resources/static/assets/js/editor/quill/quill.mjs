var quill = new Quill('#quill', {
  modules: {
    toolbar: [
      // ['bold', 'italic'],
      //[{ list: 'ordered' }, { list: 'bullet' }],
      ['link', 'image', 'video']
    ]
  },
  placeholder: '글을 작성하세요.',
  theme: 'bubble'
});
quill.on('text-change', function () {
  document.getElementById("quill_html").value = quill.root.innerHTML;
});

var Image = Quill.import('formats/image');
Image.className = 'img-fluid';
Quill.register(Image, true);

quill.on("selection-change", (range) => {
  if (range) {
      quill.theme.tooltip.show();
      quill.theme.tooltip.position(quill.getBounds(range));
  }
});
quill.getModule('toolbar').addHandler('image', function () {
  selectLocalImage();
});

function selectLocalImage() {
  const fileInput = document.createElement('input');
  fileInput.setAttribute('type', 'file');
  console.log("input.type " + fileInput.type);

  fileInput.click();

  fileInput.addEventListener("change", function () {
    const formData = new FormData();
    const file = fileInput.files[0];
    formData.append('file', file);

    $.ajax({
      type: 'post',
      enctype: 'multipart/form-data',
      url: '/board/write/upload',
      data: formData,
      processData: false,
      contentType: false,
      dataType: 'json',
      success: function (data) {
        const range = quill.getSelection();
        quill.insertEmbed(range.index, 'image', data.file.url);

      },
      error: function (err) {
        console.log(err);
      }
    });

  });
}
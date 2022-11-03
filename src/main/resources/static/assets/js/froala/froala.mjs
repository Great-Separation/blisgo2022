var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

//navbar
var navbar = document.querySelector("#transmenu");
// main, section 위치
var main = document.querySelector('main');
var mainSize = Math.floor(Number(window.getComputedStyle(main).height.replace('px', '')));
var navSpaceSize = Math.floor(Number(window.getComputedStyle(navbar).height.replace('px', '')));
var sectionSize = mainSize - navSpaceSize;

let cardHeader = document.querySelector(".card-header");

let cardH = Number(window.getComputedStyle(cardHeader).height.replace('px', ''));
console.log("cardH", cardH);
console.log("sectionSize in quill", sectionSize);
let cardSize = cardH;
console.log("cardSize", cardSize)
let wysiwygSize = Math.floor(sectionSize - cardSize - 48);
console.log("wysiwygSize>", wysiwygSize);

new FroalaEditor('#froala', {
	language: 'ko',
	height: wysiwygSize,
	charCounterCount: false,
	toolbarInline: true,
	toolbarButtons: ['insertImage', 'insertLink', 'insertTable'],
	pluginsEnabled: ['quickInsert', 'image', 'table'],
	// Set the image upload parameter.
	imageUploadParam: 'file',

	// Additional upload params.
	imageUploadParams: { id: 'froala' },

	// Set the image upload URL.
	imageUploadURL: '/board/write/upload',

	// Set request type.
	imageUploadMethod: 'POST',

	// Set max image size to 5MB.
	imageMaxSize: 5 * 1024 * 1024,

	// Allow to upload PNG and JPG.
	imageAllowedTypes: ['jpeg', 'jpg', 'png', 'webp', 'gif'],

	// Sets the default image alignment when it is inserted in the rich text editor.
	imageDefaultAlign: 'left',

	requestWithCORS: true,

	requestHeaders: {
		'X-CSRF-TOKEN': token
	  }
});
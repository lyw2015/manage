/**
 * @license Copyright (c) 2003-2018, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see https://ckeditor.com/legal/ckeditor-oss-license
 */

CKEDITOR.editorConfig = function (config) {
    config.language = 'zh-cn';
    //config.uiColor = '#f1e4db';
    config.height = 300;
    //elementspath,resize
    config.removePlugins = 'elementspath';
    config.removeButtons = 'Save,Print,Language,Flash,Iframe,CreateDiv,ShowBlocks';
    config.image_previewText = ' '; // 图片信息面板预览区内容的文字内容，默认显示CKEditor自带的内容
    config.removeDialogTabs = 'image:advanced;image:Link;image:Upload;link:advanced;link:upload;link:target'; // 页签

    config.toolbarGroups = [
        {name: 'document', groups: ['document']},
        {name: 'clipboard', groups: ['undo', 'clipboard']},
        {name: 'editing', groups: ['find', 'selection', 'spellchecker', 'editing']},
        {name: 'basicstyles', groups: ['basicstyles', 'cleanup']},
        {name: 'paragraph', groups: ['list', 'indent', 'blocks', 'align', 'bidi', 'paragraph']},
        {name: 'links', groups: ['links']},
        {name: 'insert', groups: ['insert']},
        {name: 'styles', groups: ['styles']},
        {name: 'colors', groups: ['colors']},
        {name: 'tools', groups: ['tools']},
        {name: 'others', groups: ['others']}
    ];
};

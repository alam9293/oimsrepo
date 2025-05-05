<%@ page contentType="text/css;charset=UTF-8" %> 
<%@ taglib uri="http://www.zkoss.org/dsp/web/core" prefix="c" %> 
<c:set var="project" value="cdgtaxiTheme/img"/> 

<%-------------------------------------------------------------------------------------------------------%>
<%---------------------                     CUSTOMIZED CSS                         ----------------------%>
<%-------------------------------------------------------------------------------------------------------%>

body {
	padding: 0;
}

<%-- banner --%>
.banner_shadow {
	background: #FFFFFF url('/ibs/images/banner_shadow.gif') repeat-x bottom;
}

.banner {
	height: 20px;
	padding: 8px 3px 2px 3px;
	background: transparent url('/ibs/images/logo_ibs.gif') no-repeat top left;
	text-align: right;
}

<%-- FOR MENU HEADER --%>
.z-menubar-hor, .z-menubar-ver {
	padding: 0;
	background-image: url('/ibs/images/menubar_bg.gif');
	border-width: 0;
}

.z-menubar-hor .z-menu-btn-over .z-menu-btn-l,
.z-menubar-ver .z-menu-btn-over .z-menu-btn-l,
.z-menubar-hor .z-menu-item-btn-over .z-menu-item-btn-l,
.z-menubar-ver .z-menu-item-btn-over .z-menu-item-btn-l,
.z-menubar-hor .z-menu-btn-over .z-menu-btn-r,
.z-menubar-ver .z-menu-btn-over .z-menu-btn-r,
.z-menubar-hor .z-menu-item-btn-over .z-menu-item-btn-r,
.z-menubar-ver .z-menu-item-btn-over .z-menu-item-btn-r,
.z-menubar-hor .z-menu-btn-over .z-menu-btn-m,
.z-menubar-ver .z-menu-btn-over .z-menu-btn-m,
.z-menubar-hor .z-menu-item-btn-over .z-menu-item-btn-m,
.z-menubar-ver .z-menu-item-btn-over .z-menu-item-btn-m,
.z-menubar-hor .z-menu-btn-seld .z-menu-btn-l,
.z-menubar-ver .z-menu-btn-seld .z-menu-btn-l,
.z-menubar-hor .z-menu-item-btn-seld .z-menu-item-btn-l,
.z-menubar-ver .z-menu-item-btn-seld .z-menu-item-btn-l,
.z-menubar-hor .z-menu-btn-seld .z-menu-btn-r,
.z-menubar-ver .z-menu-btn-seld .z-menu-btn-r,
.z-menubar-hor .z-menu-item-btn-seld .z-menu-item-btn-r,
.z-menubar-ver .z-menu-item-btn-seld .z-menu-item-btn-r,
.z-menubar-hor .z-menu-btn-seld .z-menu-btn-m,
.z-menubar-ver .z-menu-btn-seld .z-menu-btn-m,
.z-menubar-hor .z-menu-item-btn-seld .z-menu-item-btn-m,
.z-menubar-ver .z-menu-item-btn-seld .z-menu-item-btn-m {
	background-image: url('/ibs/images/tb-btn-side.gif');
}

a.menu, span.menu{
	color:black;
	text-decoration:none;
	cursor:pointer;
}

a.menu:hover, span.menu:hover{
	color:#7AE4FF;
}

<%-- tabpanel scrollbar and tabs --%>
.z-tabs .z-tabs-cnt {	
	LIST-STYLE: none none outside;	
	DISPLAY: block;	
	PADDING-LEFT: 0px;	
	BACKGROUND: 0px 0px;	
	MARGIN: 0px;
	height: 22px;
	WIDTH: 100%;	
	BORDER-BOTTOM: #93BCD5 1px solid;	
	ZOOM: 1
}
.z-tabs-scroll {	
	border: none;
	BACKGROUND: #326999 0px 0px;
	padding-top: 3px;	
	PADDING-BOTTOM: 2px;
	height: 22px;
	ZOOM: 1
}

.z-tabpanel-cnt {
	background: none;
}

.z-tabbox {
	background-color: #E7F3FF;
}

.z-tab-body, .z-tab-inner, .z-tab em {
	background-color: transparent!;
	background-image: url('/ibs/images/tabs-sprite.gif');
}

<%-- tabpanel text and hover --%>
.z-tab .z-tab-text {
	color: #000000;
}
.z-tab .z-tab-body:hover .z-tab-text {
	cursor: pointer;
	color: gray;
}


<%-- MENUBAR POPUP --%>
.z-menu-popup{
	border:1px solid #000000;
	background-image: url(/ibs/images/pp-bg.gif);
}

.z-menu-popup-cnt .z-menu-item-over,
.z-menu-popup-cnt .z-menu-over {	
	border: solid 1px #31699C;	
	padding: 0px;	
	background-image: none;
	background-color: #94BEDE;
}

<%-- window header --%>
.z-window-embedded-header{
	color:white;
}

.z-window-embedded-tl,
.z-window-embedded-tl-noborder {
	background-image: url('/ibs/images/wtp-l.gif');
}

.z-window-embedded-tr,
.z-window-embedded-tr-noborder {
	background-image: url('/ibs/images/wtp-r.gif');
}

.z-window-embedded-tm,
.z-window-embedded-tm-noborder {
	background-image: url('/ibs/images/wtp-m.gif');
}

<%-- FOR HYPER LINKS --%>
a.hyperlink, span.hyperlink{
	color:black;
	text-decoration:none;
	cursor:pointer;
}
a.hyperlink:hover, span.hyperlink:hover{
	color:#7AE4FF;
}
.caps{
	 text-transform: uppercase;
}

<%-- TO REMOVE HORIZONTAL SCROLLING --%>
html{
	overflow: hidden;
	overflow-y: auto;
}

<%-- CUSTOMISED PAGING --%>
.z-paging-info {
	right: 120px;
}

.z-paging-pgsz-chooser {
	position:absolute;
	right:8px;
	top:3px;
	color: #444444;
}

.z-paging-btn button {
	width: 15px;
}

<%-- FIELDS --%>
.z-datebox-inp,
.z-label,
.z-radio-cnt,
.z-checkbox-cnt,
.z-slider-pp,
input.button,
input.file,
.z-loading,
.z-errbox,
div.z-list-footer-cnt,
div.z-list-cell-cnt,
div.z-list-header-cnt {
	font-family: Verdana,Arial,Helvetica,sans-serif;
	font-size: 12px;
}

div.z-list-footer-cnt,
div.z-list-cell-cnt,
div.z-list-header-cnt {
	font-size: 11px;
}

tr.z-listbox-odd {
	background-color: #C6DFF7;
}

tr.z-list-item-over {
/*	background-color: #617A9B;*/
/*	background-color: #75AA4B;*/
	background-color: #009900;
/*	background-color: #FFEF2F;*/
}

tr.z-list-item-over div {
/*	color: #FFFFFF;*/
}

.fieldLabel {
	font-weight: bold;
	padding-left: 10px;
	color: black;
}

.required {
	background: url('/ibs/images/asterix.gif') no-repeat;
}

.header div {
	font-weight: bold;
	color: black;
}

.requiredHeader div {
	font-weight: bold;
	padding-left: 10px;
	background: url('/ibs/images/asterix.gif') no-repeat;
	color: black;
}

select {
	border: solid 1px #7F9DB9;
}

.z-window-embedded-cnt,
.z-window-embedded-body,
.z-window-overlapped-body,
.z-window-popup-body,
.z-window-highlighted-body,
.z-window-modal-body {
	background-color: #FFFFFF;
}

.z-textbox,
.z-decimalbox,
.z-intbox,
.z-longbox,
.z-doublebox,
.z-combobox-inp,
.z-datebox-inp,
.z-paging-inp,
select {
	background-image: url('/ibs/images/input_shadow.gif');
}

<%-- FIX IE6 PRINT PREVIEW NOT SHOWING COLUMN / GRID HEADER --%>
div.z-grid-header,
div.z-grid-header tr,
div.z-grid-footer,
div.z-listbox-header,
div.z-listbox-header tr,
div.z-listbox-footer {
	overflow: visible;
}
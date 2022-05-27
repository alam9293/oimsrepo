zkPgExt = {
	init: function (cmp) {
		zk.listen(cmp, "change", zkPgExt.onchange);
	},
    onchange: function (evt) {
		if (!evt) evt = window.event;
		var sel = Event.element(evt);
		cmpId = sel.id.substring(0, sel.id.lastIndexOf("!"));
		zkau.send({uuid: cmpId, cmd: "onPageSize", data: [sel.value]});
		
		Event.stop(evt);
	}
}
package com.cdgtaxi.ibs.web.component;

import java.io.IOException;
import java.io.Writer;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.render.SmartWriter;
import org.zkoss.zkmax.zul.render.PagingDefault;
import org.zkoss.zul.Paging;

public class CustomPagingRenderer extends PagingDefault {
	private final int[] pageSizes = {10, 20, 50};

	@Override
	public void render(Component comp, Writer out) throws IOException {
		final SmartWriter wh = new SmartWriter(out);
		final Paging self = (Paging) comp;
		final String zcls = self.getZclass();
		final String uuid = self.getUuid();

		wh.write("<div id=\"").write(uuid).write("\" name=\"")
		.write(uuid).write("\" z.type=\"zul.pg.Pg\"");
		wh.write(self.getOuterAttrs()).write(self.getInnerAttrs()).write(">");

		wh.write("<table cellspacing=\"0\"><tbody><tr>");
		wh.write("<td><table id=\"").write(uuid+"!tb_f")
		.write("\" name=\"").write(uuid+"!tb_f")
		.write("\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"")
		.write(zcls).write("-btn\"><tbody><tr><td><div><button type=\"button\" class=\"")
		.write(zcls).write("-first\"> </button></div></td></tr></tbody></table></td>");
		wh.write("<td><table id=\"").write(uuid+"!tb_p")
		.write("\" name=\"").write(uuid+"!tb_p")
		.write("\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"").write(zcls)
		.write("-btn\"><tbody><tr><td><div><button type=\"button\" class=\"")
		.write(zcls).write("-prev\"> </button></div></td></tr></tbody></table></td>");
		wh.write("<td><span class=\"").write(zcls).write("-sep\"/></td>");
		wh.write("<td><span class=\"").write(zcls).write("-text\"></span></td>");
		wh.write("<td><input type=\"text\" class=\"").write(zcls).write("-inp\" value=\"")
		.write(self.getActivePage() + 1).write("\" size=\"3\" id=\"").write(uuid + "!real")
		.write("\" name=\"").write(uuid + "!real")
		.write("\"/></td>");
		wh.write("<td><span class=\"").write(zcls).write("-text\">/ ").write(self.getPageCount())
		.write("</span></td>");
		wh.write("<td><span class=\"").write(zcls).write("-sep\"/></td>");
		wh.write("<td><table id=\"").write(uuid+"!tb_n")
		.write("\" name=\"").write(uuid+"!tb_n")
		.write("\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"")
		.write(zcls).write("-btn\"><tbody><tr><td><div><button type=\"button\" class=\"")
		.write(zcls).write("-next\"> </button></div></td></tr></tbody></table></td>");
		wh.write("<td><table id=\"").write(uuid+"!tb_l")
		.write("\" name=\"").write(uuid+"!tb_l")
		.write("\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"")
		.write(zcls).write("-btn\"><tbody><tr><td><div><button type=\"button\" class=\"")
		.write(zcls).write("-last\"> </button></div></td></tr></tbody></table></td>");
		wh.write("</tr></tbody></table>");

		if (self.isDetailed()) {
			wh.write(self.getInfoTags());
		}

		// WILSON: Customised page size selector
		int currPageSize = self.getPageSize();
		wh.write("<div class=\"z-paging-pgsz-chooser\">")
		.write("<select id=\"").write(uuid+"!pg_size_chooser\" z.type=\"ext.cdgtaxi.pg.PgExt\">");
		for (int pageSize : pageSizes) {
			wh.write(optionHtml(pageSize, currPageSize));
		}
		wh.write("</select> per page")
		.write("</div>");

		wh.write("</div>");
	}

	private String optionHtml(int pageSize, int currPageSize) {
		String selectedHtml = (pageSize == currPageSize) ? "selected=\"selected\"" : "";
		return "<option value=\"" + pageSize + "\"" + selectedHtml + ">" + pageSize + "</option>";
	}
}

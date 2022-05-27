package com.cdgtaxi.ibs.web.component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.zkoss.zk.scripting.Namespace;
import org.zkoss.zk.scripting.Namespaces;
import org.zkoss.zk.ui.Components;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listgroup;
import org.zkoss.zul.Listitem;

@SuppressWarnings("serial")
public class Listheader extends org.zkoss.zul.Listheader {

	class ListitemComparator extends org.zkoss.zul.ListitemComparator {
		private final Listheader _header;
		private final int _index;
		private final boolean _asc;
		private final boolean _igcase;
		private final boolean _byval;
		private final boolean _maxnull;

		public ListitemComparator(Listheader header, boolean ascending) {
			super(header, ascending, true, true);

			_index = -1;
			_header = header;
			_asc = ascending;
			_igcase = true;
			_byval = true;
			_maxnull = false;
		}

		/**
		 * Modified from org.zkoss.zul.ListitemComparator
		 */
		@SuppressWarnings("unchecked")
		@Override
		public int compare(Object o1, Object o2) {
			final int index = (_index < 0 && _header != null ? _header
					.getColumnIndex() : _index);

			Object v1, v2;
			if (o1 instanceof  Listitem) { //not live data
				final Listitem li1 = (Listitem) o1, li2 = (Listitem) o2;
				if (index < 0) {
					v1 = handleCase(li1.getValue());
					v2 = handleCase(li2.getValue());
				} else {
					List lcs1 = li1.getChildren();
					if (index >= lcs1.size()) {
						v1 = null;
					} else {
						final Listcell lc = (Listcell) lcs1.get(index);
						// defaults to lc.getLabel if lc.getValue is null
						v1 = handleCase((_byval && lc.getValue() != null) ? lc.getValue() : lc.getLabel());
					}
					List lcs2 = li2.getChildren();
					if (index >= lcs2.size()) {
						v2 = null;
					} else {
						final Listcell lc = (Listcell) lcs2.get(index);
						// defaults to lc.getLabel if lc.getValue is null
						v2 = handleCase((_byval && lc.getValue() != null) ? lc.getValue() : lc.getLabel());
					}
				}
			} else { //live data
				v1 = handleCase(o1);
				v2 = handleCase(o2);
			}

			if (v1 == null) {
				return v2 == null ? 0 : _maxnull ? 1 : -1;
			}
			if (v2 == null) {
				return _maxnull ? -1 : 1;
			}
			final int v = ((Comparable) v1).compareTo(v2);
			return _asc ? v : -v;
		}

		/**
		 * Copied from org.zkoss.zul.ListitemComparator
		 */
		private Object handleCase(Object c) {
			if (_igcase) {
				if (c instanceof  String) {
					return ((String) c).toUpperCase();
				}
				if (c instanceof  Character) {
					return new Character(Character
							.toUpperCase(((Character) c).charValue()));
				}
			}
			return c;
		}
	}

	public Listheader() {
		setSortAscending(new ListitemComparator(this, true));
		setSortDescending(new ListitemComparator(this, false));
	}
	
	
	public boolean sort(boolean ascending) {
		String dir = getSortDirection();
		if (ascending) {
			if ("ascending".equals(dir))
				return false;
		} else if ("descending".equals(dir))
			return false;

		Comparator cmpr = (ascending) ? getSortAscending() : getSortDescending();
		if (cmpr == null)
			return false;

		Listbox box = getListbox();
		if (box == null)
			return false;

		HashMap backup = new HashMap();
		Namespace ns = Namespaces.beforeInterpret(backup, this, true);
		try {
			/*ListModel model = box.getModel();
			boolean isPagingMold = box.inPagingMold();
			int activePg = (isPagingMold) ? box.getPaginal().getActivePage() : 0;
			if (model != null)
				if (model instanceof GroupsListModel) {
					((GroupsListModel) model).sort(cmpr, ascending, box.getListhead().getChildren().indexOf(this));
				} else {
					if (!(model instanceof ListModelExt))
						throw new UiException("ListModelExt must be implemented in " + model.getClass().getName());
					((ListModelExt) model).sort(cmpr, ascending);
				}
			else {
				sort0(box, cmpr);
			}*/
			
			//added by Victor
			//by default if we using list model, zkoss use the object in the model to make comparison
			//this cause extra works as we need to define the comparator for each column
			//here we bypass some logic so it use the created list item to do comparison
			boolean isPagingMold = "paging".equals(box.getMold());
			int activePg = (isPagingMold) ? box.getPaginal().getActivePage() : 0;
			sort0(box, cmpr);
			
			if (isPagingMold)
				box.getPaginal().setActivePage(activePg);

		} finally {
			Namespaces.afterInterpret(backup, ns, true);
		}

		Iterator it = box.getListhead().getChildren().iterator();
		while (it.hasNext()) {
			Listheader hd = (Listheader) it.next();
			hd.setSortDirection((ascending) ? "ascending" : (hd != this) ? "natural" : "descending");
		}

		return true;
	}
	
	private static void sort0(Listbox box, Comparator cmpr) {
		Iterator it;
		if (box.hasGroup())
			for (it = box.getGroups().iterator(); it.hasNext();) {
				Listgroup g = (Listgroup) it.next();
				Components.sort(box.getItems(), g.getIndex() + 1, g.getIndex() + 1 + g.getItemCount(), cmpr);
			}
		else
			Components.sort(box.getItems(), cmpr);
	}
}

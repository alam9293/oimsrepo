package com.cdgtaxi.ibs.common.ui;

import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.CreateEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.util.ComponentUtil;

@SuppressWarnings("serial")
public abstract class CommonSearchByCardWindow extends CommonWindow{

	protected static final String CARD_NO_ID = "cardNoTextBox";
	protected static final String CARD_NAME_ID = "cardNameComboBox";

	protected Textbox cardNoTextBox;
	protected Combobox cardNameComboBox;
	protected PmtbProduct selectedProduct;
	
	protected static Logger logger;
	
	public CommonSearchByCardWindow() {
		logger = Logger.getLogger(getClass());
	}

	public void onCreate(CreateEvent ce) throws Exception{
		
		//initiate account no integer box
		cardNoTextBox = (Textbox)getFellow(CARD_NO_ID);
		cardNoTextBox.setMaxlength(20);
		cardNoTextBox.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				onChangeCardNo();
			}
		});
		cardNoTextBox.addEventListener(Events.ON_OK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				onChangeCardNo();
			}
		});
		
		//initiate account name combo box
		cardNameComboBox = (Combobox)getFellow(CARD_NAME_ID);
		cardNameComboBox.setStyle("text-transform:uppercase");
		cardNameComboBox.addEventListener(Events.ON_CHANGING, new EventListener() {
			public void onEvent(Event e) throws Exception {
				onChangingCardName(((InputEvent)e).getValue());
			}
		});
		cardNameComboBox.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event e) throws Exception {
				onSelectCardName();
			}
		});
		cardNameComboBox.setTooltiptext("Minimum search criteria is 3 characters");
		cardNameComboBox.setAutodrop(true);
		cardNameComboBox.setAutocomplete(false);
	}

	
	public void onSelectCardName() throws InterruptedException{
		logger.debug("Card Name selected");
	
		if(cardNameComboBox.getSelectedItem()!=null){
			selectedProduct = ComponentUtil.getSelectedItem(cardNameComboBox);
			
			if(!selectedProduct.getCardNo().equals(cardNoTextBox.getText())){
				cardNoTextBox.setText(selectedProduct.getCardNo());
			}
		} else {
			selectedProduct = null;
		}
		
	}

	public abstract List<PmtbProduct> searchProductsByCardNoAndName(String cardNo, String cardName);
	
	
	public void onChangeCardNo() throws InterruptedException{
		String cardNo = cardNoTextBox.getValue();
		
		logger.debug("Searching products by Card No: " + cardNo);
		
		
		List<PmtbProduct> products = null;
		if(cardNo!=null && !"".equals(cardNo)) {
			products = searchProductsByCardNoAndName(cardNo.toString(), null);
		}
		
		buildCardNameCombobox(products);

	}

	
	public void onChangingCardName(String name) throws InterruptedException{
		logger.debug("Searching products by Card Name: " + name);

		//only begin new search if input is greater than 2
		if(name.length()<3) {
			return;
		}
		
		
		//product still the same as selected one, skip
		if(selectedProduct!=null && cardNameComboBox.getSelectedItem()!=null){
			PmtbProduct selectedProd = ComponentUtil.getSelectedItem(cardNameComboBox);
			if(selectedProd.getProductNo().equals(selectedProduct.getProductNo())){
				return;
			}
		}
		
		List<PmtbProduct> products = searchProductsByCardNoAndName(null, name);
		buildCardNameCombobox(products);

	}
	
	
	private void buildCardNameCombobox(List<PmtbProduct> products) throws InterruptedException{
		
		//Clear combo box for a new search
		cardNameComboBox.getChildren().clear();
		cardNameComboBox.setSelectedItem(null);
		
		if(products!=null && !products.isEmpty()){
		
			for(PmtbProduct product : products){
				Comboitem item = new Comboitem(product.getNameOnProduct()+" ("+product.getCardNo()+")");
				item.setValue(product);
				cardNameComboBox.appendChild(item);
			}
			if(products.size()>0){
				
				if(products.size()==1){
					cardNameComboBox.setSelectedIndex(0);
					Events.sendEvent(new Event( Events.ON_SELECT, cardNameComboBox, products.get(0)));
					
				} else {
					cardNameComboBox.open();
				}
				
			}
		} else{
			//still proceed to select product name even product is null
			onSelectCardName();
		}

	}

	
	public void reset() throws InterruptedException {
		cardNoTextBox.setValue(null);
		cardNameComboBox.setValue(null);
		cardNameComboBox.getChildren().clear();
		selectedProduct = null;
	}
	
	public void checkProductNotNull(){
		
		if(selectedProduct==null){
			throw new WrongValueException("Please fill in the valid card no.");
		}
		
	}
	
	
	public PmtbProduct getSelectedProduct(){
		
		cardNoTextBox.getValue();
		
		return selectedProduct;
	}

	@Override
	public void refresh() throws InterruptedException {
		
		
	}
}

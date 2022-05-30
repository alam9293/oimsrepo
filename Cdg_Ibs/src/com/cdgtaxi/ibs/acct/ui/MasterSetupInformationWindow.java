package com.cdgtaxi.ibs.acct.ui;

import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Detail;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Html;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

import com.cdgtaxi.ibs.common.AdminFeeMasterManager;
import com.cdgtaxi.ibs.common.CreditTermMasterManager;
import com.cdgtaxi.ibs.common.EarlyPaymentMasterManager;
import com.cdgtaxi.ibs.common.IssuanceMasterManager;
import com.cdgtaxi.ibs.common.LatePaymentMasterManager;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.ProductDiscountMasterManager;
import com.cdgtaxi.ibs.common.PromotionMasterManager;
import com.cdgtaxi.ibs.common.RewardsMasterManager;
import com.cdgtaxi.ibs.common.SubscriptionMasterManager;
import com.cdgtaxi.ibs.common.VolumeDiscountMasterManager;
import com.cdgtaxi.ibs.common.ui.CommonWindow;

public class MasterSetupInformationWindow extends CommonWindow {
	private static final long serialVersionUID = -8720888522063528660L;
	private static Logger logger = Logger.getLogger(MasterSetupInformationWindow.class);
	private String type = null;
	@SuppressWarnings("unchecked")
	public MasterSetupInformationWindow(){
		Map<String,String> map = Executions.getCurrent().getArg();
		this.type = map.get("type");
		this.setWidth("75%");
		this.setMinwidth(100);
		this.setSizable(false);
		this.setClosable(true);
	}
	public void display(){
		if(this.type.equals("productDiscount")){
			logger.info("Display product discount");
			this.setTitle("Product Discount Information");
			// looping thru each product discount
			// getting all master details for product discount
			Map<Integer, String> pdms = MasterSetup.getProductDiscountManager().getAllMasters();
			for(Integer productDiscountNo : pdms.keySet()){
				// getting product discount plan master
				// creating the product discount header
				Grid pdHeader = new Grid();
				Columns pdColumns = new Columns();
				Column pdColumn = new Column(pdms.get(productDiscountNo));
				// now appending header to window
				pdColumns.appendChild(pdColumn); pdHeader.appendChild(pdColumns); this.appendChild(pdHeader);
				// now creating the grid for the plan master and add it to window
				Grid master = new Grid(); this.appendChild(master);
				// creating a columns and add to grid.
				Columns columns = new Columns(); master.appendChild(columns);
				// setting sizable to false
				columns.setSizable(false);
				// now creating 3 different columns
				Column column = new Column();
				column.setWidth("2%");
				columns.appendChild(column);
				column = new Column("Product Discount");
				column.setWidth("49%");
				columns.appendChild(column);
				column = new Column("Effective Date");
				column.setWidth("49%");
				columns.appendChild(column);
				// creating rows and adding it to the master
				Rows rows = new Rows(); master.appendChild(rows);
				// getting each detail
				Map<Integer, Map<String, String>> pdpds = MasterSetup.getProductDiscountManager().getCurrentDetail(productDiscountNo);
				// looping thru each details
				if(pdpds!=null){
					for(Integer pdpd : pdpds.keySet()){
						// creating row and add it to rows
						Row row = new Row(); rows.appendChild(row);
						// creating numbering label and add it to row
						row.appendChild(new Label(""+(row.getChildren().size()+1)));
						// creating product discount label and add it to row
						row.appendChild(new Label(pdpds.get(pdpd).get(ProductDiscountMasterManager.DETAIL_PRODUCT_DISCOUNT)));
						// creating effective date label and add it to row
						row.appendChild(new Label(pdpds.get(pdpd).get(ProductDiscountMasterManager.DETAIL_EFFECTIVE_DATE)));
					}
				}
				// creating a break line
				Html html = new Html("<br>");
				this.appendChild(html);
			}
			// removing last break line to looks neater
			if(this.getLastChild()!=null){
				this.removeChild(this.getLastChild());
			}
		}else if(this.type.equals("volumeDiscount")){
			logger.info("Display volume discount");
			this.setTitle("Volume Discount Information");
			// getting all master details for volume discount
			Map<Integer, String> vdms = MasterSetup.getVolumeDiscountManager().getAllMasters();
			// looping thru each volume discount
			for(Integer volumeDiscountNo : vdms.keySet()){
				// creating the volume discount header
				Grid vdHeader = new Grid();
				Columns vdColumns = new Columns();
				Column vdColumn = new Column(vdms.get(volumeDiscountNo));
				// now appending header to window
				this.appendChild(vdHeader); vdHeader.appendChild(vdColumns); vdColumns.appendChild(vdColumn); 
				// now creating the grid for the plan master and add it to window
				Grid master = new Grid(); this.appendChild(master);
				// creating a columns and add to grid.
				Columns columns = new Columns(); master.appendChild(columns);
				// setting sizable to false
				columns.setSizable(false);
				// now creating 4 different columns
				Column column = new Column();				column.setWidth("2%"); columns.appendChild(column);
				column = new Column();						column.setWidth("2%"); columns.appendChild(column);
				column = new Column("Discount Type");		column.setWidth("48%"); columns.appendChild(column);
				column = new Column("Effective Date");		column.setWidth("48%"); columns.appendChild(column);
				// creating rows and adding it to the master
				Rows rows = new Rows(); master.appendChild(rows);
				// getting each detail
				Map<Integer, Map<String, String>> vdpds = MasterSetup.getVolumeDiscountManager().getCurrentDetail(volumeDiscountNo);
				if(vdpds!=null){
					for(Integer vdpd : vdpds.keySet()){
						// creating row and add it to rows
						Row row = new Row(); rows.appendChild(row);
						// creating detail for each tiers and adding detail to row
						Detail detail = new Detail(); row.appendChild(detail);
						// creating an inner grid and add it to detail
						Grid vdTiers = new Grid(); detail.appendChild(vdTiers);
						// creating 4 columns for grid
						columns = new Columns();					vdTiers.appendChild(columns);
						column = new Column();						column.setWidth("1%");	columns.appendChild(column);
						column = new Column("Start Range");			column.setWidth("33%");	columns.appendChild(column);
						column = new Column("End Range");			column.setWidth("33%");	columns.appendChild(column);
						column = new Column("Volume Discount");		column.setWidth("33%");	columns.appendChild(column);
						// creating rows for detail
						Rows rptRows = new Rows(); vdTiers.appendChild(rptRows);
						// getting each tier
						Map<Integer, Map<String, String>> vdpts = MasterSetup.getVolumeDiscountManager().getAllTiers(vdpd);
						for(Integer vdpt : vdpts.keySet()){
							Row rptRow = new Row(); rptRows.appendChild(rptRow);
							rptRow.appendChild(new Label(""+rptRows.getChildren().size()));
							rptRow.appendChild(new Label(vdpts.get(vdpt).get(VolumeDiscountMasterManager.TIER_START_RANGE)));
							rptRow.appendChild(new Label(vdpts.get(vdpt).get(VolumeDiscountMasterManager.TIER_END_RANGE)));
							rptRow.appendChild(new Label(vdpts.get(vdpt).get(VolumeDiscountMasterManager.TIER_VOLUME_DISCOUNT)));
						}
						// creating numbering label and add it to row
						row.appendChild(new Label(""+(rows.getChildren().size())));
						// creating product discount label and add it to row
						row.appendChild(new Label(NonConfigurableConstants.REWARDS_TYPE.get(vdpds.get(vdpd).get(VolumeDiscountMasterManager.DETAIL_DISCOUNT_TYPE))));
						// creating effective date label and add it to row
						row.appendChild(new Label(vdpds.get(vdpd).get(VolumeDiscountMasterManager.DETAIL_EFFECTIVE_DATE)));
					}
				}
				// creating a break line
				Html html = new Html("<br>");
				this.appendChild(html);
			}
			// removing last break line to looks neater
			if(this.getLastChild()!=null){
				this.removeChild(this.getLastChild());
			}
		}else if(this.type.equals("rewards")){
			logger.info("Display rewards");
			this.setTitle("Loyalty Plans Information");
			// getting all master details for rewards
			Map<Integer, String> rms = MasterSetup.getRewardsManager().getAllMasters();
			for(Integer rewardsPlanNo : rms.keySet()){
				// creating the rewards header
				Grid rHeader = new Grid();
				Columns rColumns = new Columns();
				Column rColumn = new Column(rms.get(rewardsPlanNo));
				// now appending header to window
				this.appendChild(rHeader); rHeader.appendChild(rColumns); rColumns.appendChild(rColumn); 
				// now creating the grid for the plan master and add it to window
				Grid master = new Grid(); this.appendChild(master);
				// creating a columns and add to grid.
				Columns columns = new Columns(); master.appendChild(columns);
				// setting sizable to false
				columns.setSizable(false);
				// now creating 4 different columns
				Column column = new Column();				column.setWidth("2%"); columns.appendChild(column);
				column = new Column();						column.setWidth("2%"); columns.appendChild(column);
				column = new Column("Rewards Type");		column.setWidth("32%"); columns.appendChild(column);
				column = new Column("Effective Date From");	column.setWidth("32%"); columns.appendChild(column);
				column = new Column("Effective Date To");	column.setWidth("32%"); columns.appendChild(column);
				// creating rows and adding it to the master
				Rows rows = new Rows(); master.appendChild(rows);
				// getting each detail
				Map<Integer, Map<String, String>> rpds = MasterSetup.getRewardsManager().getCurrentDetail(rewardsPlanNo);
				if(rpds!=null){
					for(Integer rpd : rpds.keySet()){
						// creating row and add it to rows
						Row row = new Row(); rows.appendChild(row);
						// creating detail for each tiers and adding detail to row
						Detail detail = new Detail(); row.appendChild(detail);
						// creating an inner grid and add it to detail
						Grid rewardsTiers = new Grid(); detail.appendChild(rewardsTiers);
						// creating 4 columns for grid
						columns = new Columns();					rewardsTiers.appendChild(columns);
						column = new Column();						column.setWidth("1%");	columns.appendChild(column);
						column = new Column("Start Range");			column.setWidth("33%");	columns.appendChild(column);
						column = new Column("End Range");			column.setWidth("33%");	columns.appendChild(column);
						column = new Column("Points Per Value");	column.setWidth("33%");	columns.appendChild(column);
						// creating rows for detail
						Rows rptRows = new Rows(); rewardsTiers.appendChild(rptRows);
						// getting each tier
						Map<Integer, Map<String, String>> rpts = MasterSetup.getRewardsManager().getAllTiers(rpd);
						for(Integer rpt : rpts.keySet()){
							Row rptRow = new Row(); rptRows.appendChild(rptRow);
							rptRow.appendChild(new Label(""+rptRows.getChildren().size()));
							rptRow.appendChild(new Label(rpts.get(rpt).get(RewardsMasterManager.TIER_START_RANGE)));
							rptRow.appendChild(new Label(rpts.get(rpt).get(RewardsMasterManager.TIER_END_RANGE)));
							rptRow.appendChild(new Label(rpts.get(rpt).get(RewardsMasterManager.TIER_POINTS_PER_VALUE)));
						}
						// creating numbering label and add it to row
						row.appendChild(new Label(""+(rows.getChildren().size())));
						// creating product discount label and add it to row
						row.appendChild(new Label(NonConfigurableConstants.REWARDS_TYPE.get(rpds.get(rpd).get(RewardsMasterManager.DETAIL_REWARDS_TYPE))));
						// creating effective date from label and add it to row
						row.appendChild(new Label(rpds.get(rpd).get(RewardsMasterManager.DETAIL_EFFECTIVE_FROM)));
						// creating effective date from label and add it to row
						row.appendChild(new Label(rpds.get(rpd).get(RewardsMasterManager.DETAIL_EFFECTIVE_TO)));
					}
				}
				// creating a break line
				Html html = new Html("<br>");
				this.appendChild(html);
			}
			// removing last break line to looks neater
			if(this.getLastChild()!=null){
				this.removeChild(this.getLastChild());
			}
		}else if(this.type.equals("subscribeFee")){
			logger.info("Display subscription fee");
			this.setTitle("Subscription Fee Information");
			// getting all master details for subscription
			Map<Integer, String> sfms = MasterSetup.getSubscriptionManager().getAllMasters();
			for(Integer subscriptionNo : sfms.keySet()){
				// creating the subscription header
				Grid sfHeader = new Grid();
				Columns sfColumns = new Columns();
				Column sfColumn = new Column(sfms.get(subscriptionNo));
				// now appending header to window
				this.appendChild(sfHeader); sfHeader.appendChild(sfColumns); sfColumns.appendChild(sfColumn);
				// now creating the grid for the plan master and add it to window
				Grid master = new Grid(); this.appendChild(master);
				// creating a columns and add to grid.
				Columns columns = new Columns(); master.appendChild(columns);
				// setting sizable to false
				columns.setSizable(false);
				// now creating 3 different columns
				Column column = new Column();
				column.setWidth("2%");
				columns.appendChild(column);
				column = new Column("Subscription Fee");
				column.setWidth("49%");
				columns.appendChild(column);
				column = new Column("Effective Date");
				column.setWidth("49%");
				columns.appendChild(column);
				// creating rows and adding it to the master
				Rows rows = new Rows(); master.appendChild(rows);
				// getting each detail
				Map<Integer, Map<String, String>> sfds = MasterSetup.getSubscriptionManager().getCurrentDetail(subscriptionNo);
				if(sfds!=null){
					for(Integer sfd : sfds.keySet()){
						// creating row and add it to rows
						Row row = new Row(); rows.appendChild(row);
						// creating numbering label and add it to row
						row.appendChild(new Label(""+(rows.getChildren().size())));
						// creating product discount label and add it to row
						row.appendChild(new Label(sfds.get(sfd).get(SubscriptionMasterManager.DETAIL_SUBSCRIPTION_FEE)));
						// creating effective date label and add it to row
						row.appendChild(new Label(sfds.get(sfd).get(SubscriptionMasterManager.DETAIL_EFFECTIVE_DATE)));
					}
				}
				// creating a break line
				Html html = new Html("<br>");
				this.appendChild(html);
			}
			// removing last break line to looks neater
			if(this.getLastChild()!=null){
				this.removeChild(this.getLastChild());
			}
		} else if(this.type.equals("issuanceFee")){
			logger.info("Display issuance fee");
			this.setTitle("Issuance Fee Information");
			// getting all master details for subscription
			Map<Integer, String> sfms = MasterSetup.getIssuanceManager().getAllMasters();
			for(Integer issuanceNo : sfms.keySet()){
				// creating the subscription header
				Grid sfHeader = new Grid();
				Columns sfColumns = new Columns();
				Column sfColumn = new Column(sfms.get(issuanceNo));
				// now appending header to window
				this.appendChild(sfHeader); sfHeader.appendChild(sfColumns); sfColumns.appendChild(sfColumn);
				// now creating the grid for the plan master and add it to window
				Grid master = new Grid(); this.appendChild(master);
				// creating a columns and add to grid.
				Columns columns = new Columns(); master.appendChild(columns);
				// setting sizable to false
				columns.setSizable(false);
				// now creating 3 different columns
				Column column = new Column();
				column.setWidth("2%");
				columns.appendChild(column);
				column = new Column("Issuance Fee");
				column.setWidth("49%");
				columns.appendChild(column);
				column = new Column("Effective Date");
				column.setWidth("49%");
				columns.appendChild(column);
				// creating rows and adding it to the master
				Rows rows = new Rows(); master.appendChild(rows);
				// getting each detail
				Map<Integer, Map<String, String>> sfds = MasterSetup.getIssuanceManager().getCurrentDetail(issuanceNo);
				if(sfds!=null){
					for(Integer sfd : sfds.keySet()){
						// creating row and add it to rows
						Row row = new Row(); rows.appendChild(row);
						// creating numbering label and add it to row
						row.appendChild(new Label(""+(rows.getChildren().size())));
						// creating product discount label and add it to row
						row.appendChild(new Label(sfds.get(sfd).get(IssuanceMasterManager.DETAIL_ISSUANCE_FEE)));
						// creating effective date label and add it to row
						row.appendChild(new Label(sfds.get(sfd).get(IssuanceMasterManager.DETAIL_EFFECTIVE_DATE)));
					}
				}
				// creating a break line
				Html html = new Html("<br>");
				this.appendChild(html);
			}
			// removing last break line to looks neater
			if(this.getLastChild()!=null){
				this.removeChild(this.getLastChild());
			}
		} else if(this.type.equals("adminFee")){
			logger.info("Display admin fee");
			this.setTitle("Admin Fees Information");
			// getting all master details for admin fee
			Map<Integer, String> afms = MasterSetup.getAdminFeeManager().getAllMasters();
			for(Integer adminFeeNo : afms.keySet()){
				// creating the admin fee header
				Grid afHeader = new Grid();
				Columns afColumns = new Columns();
				Column afColumn = new Column(afms.get(adminFeeNo));
				// now appending header to window
				this.appendChild(afHeader); afHeader.appendChild(afColumns); afColumns.appendChild(afColumn);
				// now creating the grid for the plan master and add it to window
				Grid master = new Grid(); this.appendChild(master);
				// creating a columns and add to grid.
				Columns columns = new Columns(); master.appendChild(columns);
				// setting sizable to false
				columns.setSizable(false);
				// now creating 3 different columns
				Column column = new Column();
				column.setWidth("2%");
				columns.appendChild(column);
				column = new Column("Admin Fee");
				column.setWidth("49%");
				columns.appendChild(column);
				column = new Column("Effective Date");
				column.setWidth("49%");
				columns.appendChild(column);
				// creating rows and adding it to the master
				Rows rows = new Rows(); master.appendChild(rows);
				// getting each detail
				Map<Integer, Map<String, String>> afds = MasterSetup.getAdminFeeManager().getCurrentDetail(adminFeeNo);
				if(afds!=null){
					for(Integer afd : afds.keySet()){
						// creating row and add it to rows
						Row row = new Row(); rows.appendChild(row);
						// creating numbering label and add it to row
						row.appendChild(new Label(""+(rows.getChildren().size())));
						// creating product discount label and add it to row
						row.appendChild(new Label(afds.get(afd).get(AdminFeeMasterManager.DETAIL_ADMIN_FEE)));
						// creating effective date label and add it to row
						row.appendChild(new Label(afds.get(afd).get(AdminFeeMasterManager.DETAIL_EFFECTIVE_DATE)));
					}
				}
				// creating a break line
				Html html = new Html("<br>");
				this.appendChild(html);
			}
			// removing last break line to looks neater
			if(this.getLastChild()!=null){
				this.removeChild(this.getLastChild());
			}
		}else if(this.type.equals("earlyDiscount")){
			logger.info("Display early payment");
			this.setTitle("Early Payment Discount Information");
			// getting all master details for admin fee
			Map<Integer, String> epms = MasterSetup.getEarlyPaymentManager().getAllMasters();
			for(Integer earlyPaymentNo : epms.keySet()){
				// creating the early payment header
				Grid epHeader = new Grid();
				Columns epColumns = new Columns();
				Column epColumn = new Column(epms.get(earlyPaymentNo));
				// now appending header to window
				this.appendChild(epHeader); epHeader.appendChild(epColumns); epColumns.appendChild(epColumn);
				// now creating the grid for the plan master and add it to window
				Grid master = new Grid(); this.appendChild(master);
				// creating a columns and add to grid.
				Columns columns = new Columns(); master.appendChild(columns);
				// setting sizable to false
				columns.setSizable(false);
				// now creating 3 different columns
				Column column = new Column();
				column.setWidth("2%");
				columns.appendChild(column);
				column = new Column("Early Payment Discount");
				column.setWidth("49%");
				columns.appendChild(column);
				column = new Column("Effective Date");
				column.setWidth("49%");
				columns.appendChild(column);
				// creating rows and adding it to the master
				Rows rows = new Rows(); master.appendChild(rows);
				// getting each detail
				Map<Integer, Map<String, String>> epds = MasterSetup.getEarlyPaymentManager().getCurrentDetail(earlyPaymentNo);
				if(epds!=null){
					for(Integer epd : epds.keySet()){
						// creating row and add it to rows
						Row row = new Row(); rows.appendChild(row);
						// creating numbering label and add it to row
						row.appendChild(new Label(""+(rows.getChildren().size())));
						// creating product discount label and add it to row
						row.appendChild(new Label(epds.get(epd).get(EarlyPaymentMasterManager.EARLY_PAYMENT)));
						// creating effective date label and add it to row
						row.appendChild(new Label(epds.get(epd).get(EarlyPaymentMasterManager.EFFECTIVE_DATE)));
					}
				}
				// creating a break line
				Html html = new Html("<br>");
				this.appendChild(html);
			}
			// removing last break line to looks neater
			if(this.getLastChild()!=null){
				this.removeChild(this.getLastChild());
			}
		}else if(this.type.equals("lateInterest")){
			logger.info("Display late interest");
			this.setTitle("Late Payment Interest Information");
			// getting all master details for admin fee
			Map<Integer, String> lpms = MasterSetup.getLatePaymentManager().getAllMasters();
			for(Integer latePaymentNo : lpms.keySet()){
				// creating the late payment header
				Grid lpHeader = new Grid();
				Columns lpColumns = new Columns();
				Column lpColumn = new Column(lpms.get(latePaymentNo));
				// now appending header to window
				this.appendChild(lpHeader); lpHeader.appendChild(lpColumns); lpColumns.appendChild(lpColumn);
				// now creating the grid for the plan master and add it to window
				Grid master = new Grid(); this.appendChild(master);
				// creating a columns and add to grid.
				Columns columns = new Columns(); master.appendChild(columns);
				// setting sizable to false
				columns.setSizable(false);
				// now creating 3 different columns
				Column column = new Column();
				column.setWidth("2%");
				columns.appendChild(column);
				column = new Column("Late Payment Interest");
				column.setWidth("49%");
				columns.appendChild(column);
				column = new Column("Effective Date");
				column.setWidth("49%");
				columns.appendChild(column);
				// creating rows and adding it to the master
				Rows rows = new Rows(); master.appendChild(rows);
				// getting each detail
				Map<Integer, Map<String, String>> lpds = MasterSetup.getLatePaymentManager().getCurrentDetail(latePaymentNo);
				if(lpds!=null){
					for(Integer lpd : lpds.keySet()){
						// creating row and add it to rows
						Row row = new Row(); rows.appendChild(row);
						// creating numbering label and add it to row
						row.appendChild(new Label(""+(rows.getChildren().size())));
						// creating product discount label and add it to row
						row.appendChild(new Label(lpds.get(lpd).get(LatePaymentMasterManager.LATE_PAYMENT)));
						// creating effective date label and add it to row
						row.appendChild(new Label(lpds.get(lpd).get(LatePaymentMasterManager.EFFECTIVE_DATE)));
					}
				}
				// creating a break line
				Html html = new Html("<br>");
				this.appendChild(html);
			}
			// removing last break line to looks neater
			if(this.getLastChild()!=null){
				this.removeChild(this.getLastChild());
			}
		}else if(this.type.equals("creditTerm")){
			logger.info("Display credit term");
			this.setTitle("Credit Term Information");
			// getting all master details for credit term
			Map<Integer, String> ctms = MasterSetup.getCreditTermManager().getAllMasters();
			for(Integer creditTermNo : ctms.keySet()){
				// creating the credit term header
				Grid ctHeader = new Grid();
				Columns ctColumns = new Columns();
				Column ctColumn = new Column(ctms.get(creditTermNo));
				// now appending header to window
				this.appendChild(ctHeader); ctHeader.appendChild(ctColumns); ctColumns.appendChild(ctColumn);
				// now creating the grid for the plan master and add it to window
				Grid master = new Grid(); this.appendChild(master);
				// creating a columns and add to grid.
				Columns columns = new Columns(); master.appendChild(columns);
				// setting sizable to false
				columns.setSizable(false);
				// now creating 3 different columns
				Column column = new Column();
				column.setWidth("2%");
				columns.appendChild(column);
				column = new Column("Credit Term");
				column.setWidth("49%");
				columns.appendChild(column);
				column = new Column("Effective Date");
				column.setWidth("49%");
				columns.appendChild(column);
				// creating rows and adding it to the master
				Rows rows = new Rows(); master.appendChild(rows);
				// getting each detail
				Map<Integer, Map<String, String>> ctds = MasterSetup.getCreditTermManager().getCurrentDetail(creditTermNo);
				if(ctds!=null){
					for(Integer ctd : ctds.keySet()){
						// creating row and add it to rows
						Row row = new Row(); rows.appendChild(row);
						// creating numbering label and add it to row
						row.appendChild(new Label(""+(rows.getChildren().size())));
						// creating product discount label and add it to row
						row.appendChild(new Label(ctds.get(ctd).get(CreditTermMasterManager.DETAIL_CREDIT_TERM)));
						// creating effective date label and add it to row
						row.appendChild(new Label(ctds.get(ctd).get(CreditTermMasterManager.DETAIL_EFFECTIVE_DATE)));
					}
				}
				// creating a break line
				Html html = new Html("<br>");
				this.appendChild(html);
			}
			// removing last break line to looks neater
			if(this.getLastChild()!=null){
				this.removeChild(this.getLastChild());
			}
		}else if(this.type.equals("promotion")){
			logger.info("Display promotions");
			this.setTitle("Promotion Information");
			// getting all master details for rewards
			Map<Integer, Map<String, String>> pms = MasterSetup.getPromotionManager().getAllAccountPromotions();
			// creating the promotion header
			Grid pHeader = new Grid();
			Columns pColumns = new Columns();
			Column pColumn = new Column("Promotions");
			// now appending header to window
			this.appendChild(pHeader); pHeader.appendChild(pColumns); pColumns.appendChild(pColumn); 
			// now creating the grid for the plan master and add it to window
			Grid master = new Grid(); this.appendChild(master);
			// creating a columns and add to grid.
			Columns columns = new Columns(); master.appendChild(columns);
			// setting sizable to false
			columns.setSizable(false);
			// now creating 4 different columns
			Column column = new Column();				column.setWidth("2%"); columns.appendChild(column);
			column = new Column("Name");				column.setWidth("11%"); columns.appendChild(column);
			column = new Column("Type");				column.setWidth("8%"); columns.appendChild(column);
			column = new Column("Product Type");		column.setWidth("11%"); columns.appendChild(column);
			column = new Column("Promo Type");			column.setWidth("10%"); columns.appendChild(column);
			column = new Column("Promo Value");			column.setWidth("10%"); columns.appendChild(column);
			column = new Column("Eff. Trip Date From");	column.setWidth("14%"); columns.appendChild(column);
			column = new Column("Eff. Trip Date To");	column.setWidth("12%"); columns.appendChild(column);
			column = new Column("Job Type");			column.setWidth("11%"); columns.appendChild(column);
			column = new Column("Vehicle Model");		column.setWidth("11%"); columns.appendChild(column);
			// creating rows and adding it to the master
			Rows rows = new Rows(); master.appendChild(rows);
			// getting each detail
			for(Integer promotionNo : pms.keySet()){
				Map<String, String> details = pms.get(promotionNo);
				// creating row and add it to rows
				Row row = new Row(); rows.appendChild(row);
				// creating numbering
				row.appendChild(new Label(""+(rows.getChildren().size())));
				// creating promotion label and add it to row
				row.appendChild(new Label(details.get(PromotionMasterManager.MASTER_NAME)));
				// creating acct type and add it to row
				row.appendChild(new Label(details.get(PromotionMasterManager.MASTER_TYPE)));
				// creating product type and add it to row
				row.appendChild(new Label(details.get(PromotionMasterManager.MASTER_PRODUCT_TYPE)));
				// creating promotion type and add it to row
				row.appendChild(new Label(details.get(PromotionMasterManager.MASTER_PROMO_TYPE)));
				// creating promotion value and add it to row
				row.appendChild(new Label(details.get(PromotionMasterManager.MASTER_PROMO_VALUE)));
				// creating effective date from and add it to row
				row.appendChild(new Label(details.get(PromotionMasterManager.MASTER_EFF_DATE_FROM)));
				// creating effective date to and add it to row
				row.appendChild(new Label(details.get(PromotionMasterManager.MASTER_EFF_DATE_TO)));
				// creating job type and add it to row
				row.appendChild(new Label(details.get(PromotionMasterManager.MASTER_JOB)));
				// creating vehicle model and add it to row
				row.appendChild(new Label(details.get(PromotionMasterManager.MASTER_MODEL)));
				// creating a break line
				Html html = new Html("<br>");
				this.appendChild(html);
			}
			// removing last break line to looks neater
			if(this.getLastChild()!=null){
				this.removeChild(this.getLastChild());
			}
		}
	}
	@Override
	public void refresh() throws InterruptedException {
		this.getChildren().clear();
	}
}
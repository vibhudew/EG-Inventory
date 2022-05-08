package model;

public class Inventory {

	private int invID;
	private String invItemCode;
	private String invItemName;
	private int stockQty;
	private String manufactYr;
	private String latestRepairDate;
	private String handledBy;
	private String createdTime;
	private String updatedTime;

	public Inventory() {
		super();

	}

	public Inventory(int invID, String invItemCode, String invItemName, int stockQty, String manufactYr,
			String latestRepairDate, String handledBy, String createdTime, String updatedTime) {
		super();
		this.invID = invID;
		this.invItemCode = invItemCode;
		this.invItemName = invItemName;
		this.stockQty = stockQty;
		this.manufactYr = manufactYr;
		this.latestRepairDate = latestRepairDate;
		this.handledBy = handledBy;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
	}


	public Inventory(String invItemCode, String invItemName, int stockQty, String manufactYr, String latestRepairDate,
			String handledBy) {
		super();
		this.invItemCode = invItemCode;
		this.invItemName = invItemName;
		this.stockQty = stockQty;
		this.manufactYr = manufactYr;
		this.latestRepairDate = latestRepairDate;
		this.handledBy = handledBy;
	}
	
	public Inventory(String invItemCode, String invItemName, int stockQty, String manufactYr, String latestRepairDate) {
		super();
		this.invItemCode = invItemCode;
		this.invItemName = invItemName;
		this.stockQty = stockQty;
		this.manufactYr = manufactYr;
		this.latestRepairDate = latestRepairDate;
	}

	public int getInvID() {
		return invID;
	}

	public String getInvItemCode() {
		return invItemCode;
	}

	public String getInvItemName() {
		return invItemName;
	}

	public int getStockQty() {
		return stockQty;
	}

	public String getManufactYr() {
		return manufactYr;
	}

	public String getLatestRepairDate() {
		return latestRepairDate;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public String getUpdatedTime() {
		return updatedTime;
	}

	public String getHandledBy() {
		return handledBy;
	}

}
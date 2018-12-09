package init.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cucumber.listener.Reporter;

public class BrowserAction {

	static Logger logger = Logger.getLogger("BrowserAction.class");

	/** The base_path. */
	private static String base_path = System.getProperty("user.dir");

	static String snapshotPath = null;

	public BrowserAction() {
		// PropertyConfigurator.configure("log4j.properties");
	}

	/*
	 * protected WebElement elementClick( String locator, String locatorValue )
	 * throws IOException { getDriver(); waitForPageLoad( locator ); WebElement
	 * element = waitForElement( locator, WaitingTypes_enum.CLICKABILITY );
	 * element.click();
	 * 
	 * WebDriver driver = Constant.getDriver(); WebDriverWait wait = new
	 * WebDriverWait(driver, 10); //WebElement element =
	 * wait.until(ExpectedConditions.elementToBeClickable(By.id("someid")));
	 * 
	 * //Converting locator to string //String locatorString =
	 * BrowserAction.convertor(locator);
	 * 
	 * switch(locator){ case "id":
	 * wait.until(ExpectedConditions.elementToBeClickable(By.id(locatorValue)));
	 * 
	 * case "xpath":
	 * wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locatorValue)
	 * ));
	 * 
	 * case "linkText":
	 * wait.until(ExpectedConditions.elementToBeClickable(By.linkText(
	 * locatorValue)));
	 * 
	 * case "cssSelector":
	 * wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
	 * locatorValue))); } return null; }
	 */

	// To verify element is displayed or not
	public static boolean verifyElementDisplayed(WebDriver driver, By locator) {
		try {
			if (driver.findElement(locator).isDisplayed()) {
				logger.debug("viewElementDisplayed with locator : " + locator);
				return true;
			} else {
				logger.debug("viewElement not displayed with locator : " + locator);
				return false;
			}
		} catch (NoSuchElementException ex) {
			logger.error("NoSuchElementException in viewElementDisplayed <{}> " + ex);
		} catch (Exception ex) {
			logger.error("Exception in viewElementDisplayed method of BrowserActions class <{}>" + ex);
		}
		return false;
	}

	// Wait for element
	public void elementWait(int timeout) throws InterruptedException {
		try {
			Thread.sleep(timeout);
			logger.debug("Wait for element upto " + timeout + " Seconds ");
		} catch (TimeoutException ex) {
			logger.error("Timeout Exception in elementWait method of BrowserActions class <{}>" + ex);
		} catch (Exception ex) {
			logger.error("Exception in elementWait method of BrowserActions class <{}>" + ex);
		}
	}

	// Wait to load the page
	public boolean waitForPageLoad(WebDriver driver, By locator) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

			logger.debug("waitForPageLoad with locator : " + locator);

			if (element != null)
				return true;
		} catch (NoSuchElementException ex) {
			logger.error("NoSuchElementException in waitForPageLoad method  of BrowserActions class <{}> " + ex);
		} catch (TimeoutException ex) {
			logger.error("Timeout Exception in waitForPageLoad method of BrowserActions class <{}>" + ex);
		} catch (Exception ex) {
			logger.error("Exception in waitForPageLoad method of BrowserActions class <{}>" + ex);
		}
		return false;
	}

	// To click button/element
	public static void clickButton(WebDriver driver, By locator) throws Exception {
		try {
			driver.findElement(locator).click();
			logger.debug("clickButton with locator : " + locator);
		} catch (Exception ex) {
			Reporter.setTestRunnerOutput("Exception in clickButton method of BrowserActions class <{}>" + ex);
			takeSnapShot(driver);
			//Reporter.addScreenCaptureFromPath(snapshotPath);
			logger.error("Exception in clickButton method of BrowserActions class <{}>" + ex);
			Reporter.addStepLog("Exception in clickButton method of BrowserActions class");
			throw new NoSuchElementException();
		}
	}

	// Get text
	public static String getTxt(WebDriver driver, By locator) throws Exception {
		try {
			String text = driver.findElement(locator).getText();
			logger.debug("Get text with locator : " + locator);
			return text;
		} catch (Exception ex) {
			logger.error("Exception in getTxt method of BrowserActions class <{}>" + ex);
			Reporter.setTestRunnerOutput("Exception in getTxt method of BrowserActions class <{}>" + ex);
			takeSnapShot(driver);
			//Reporter.addScreenCaptureFromPath(snapshotPath);
			logger.error("Exception in getTxt method of BrowserActions class <{}>" + ex);
			Reporter.addStepLog("Exception in getTxt method of BrowserActions class");
			throw new NoSuchElementException();
		}
	}

	// To enter text in text box
	public static void enterText(WebDriver driver, By locator, String enterText) throws Exception {
		try {
			System.out.println(enterText);
			driver.findElement(locator).sendKeys(enterText);
			logger.debug("Enter text with locator : " + locator);
		} catch (NoSuchElementException ex) {
			takeSnapShot(driver);
			Reporter.addScreenCaptureFromPath(snapshotPath);
			logger.error("NoSuchElementException in enterText method  of BrowserActions class <{}> " + ex);
		} catch (Exception ex) {
			logger.error("Exception in enterText method of BrowserActions class <{}>" + ex);
		}
	}

	/*
	 * public static String convertor(By locator) { //Convert By type to String
	 * String locatorString = locator.toString();
	 * System.out.println(locatorString);
	 * 
	 * int i = locatorString.indexOf(' ');// id: String loctor =
	 * locatorString.substring(3, i); System.out.println("first :"+loctor);
	 * 
	 * //Remove ' : ' in the last loctor = loctor.replaceAll(":", "");//id
	 * return loctor; }
	 */

	// Find element and return it
	public WebElement getElement(WebDriver driver, By locator) {
		try {
			logger.debug("getElement with locator : " + locator);
			return driver.findElement(locator);
		} catch (Exception ex) {
			logger.error("Exception in getElement method of BrowserActions class <{}>" + ex);
		}
		return null;
	}

	// Hover and submenu
	public static void hoverToSubmenu(WebDriver driver, WebElement hoverElement) throws InterruptedException {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(hoverElement).build().perform();
			action.moveToElement(hoverElement).click().build().perform();
			logger.debug("hoverToSubmenu with locator : " + hoverElement);
		} catch (Exception ex) {
			logger.error("Exception in hoverToSubmenu method of BrowserActions class <{}>" + ex);
		}
	}

	// Wait upto loading indicator is displayed
	public static void loadingIndicator(WebDriver driver) throws InterruptedException {
		try {
			WebElement element = driver.findElement(By.id("ctl00_Img1"));
			for (int i = 1; i <= 60; i++) {
				if (element.isDisplayed() == true) {
					System.out.println("loading indicator is displaying");
					logger.debug("Loading indicator is displaying");
					Thread.sleep(1000);
				} else {
					System.out.println("element is not displaying");
					logger.debug("Now element is displaying");
					break;
				}
			}
		} catch (Exception ex) {
			logger.error("Exception in loadingIndicator method of BrowserActions class <{}>" + ex);
		}
	}

	// select value from dropdown
	public static void dropDownValue(WebDriver driver, By locator, String selectValue) {
		try {
			WebElement elementDropDown = driver.findElement(locator);
			Select ddp = new Select(elementDropDown);
			ddp.selectByVisibleText(selectValue);
			logger.debug("Value is selecting from dropdown");
		} catch (Exception ex) {
			logger.error("Exception in dropDownValue method of BrowserActions class <{}>" + ex);
		}
	}

	// To delete characters from string and return it.
	public static String trimString(String oginalString, int startIndex, int enfIndex) {
		try {
			StringBuffer sb = new StringBuffer(oginalString);
			sb.delete(startIndex, enfIndex);
			return sb.toString();
		} catch (Exception ex) {
			logger.error("Exception in trimString method of BrowserActions class <{}>" + ex);
		}
		return null;
	}

	// Read the excel and return values in ArrayList<>
	public ArrayList<String> readExcel() throws IOException {
		try {
			// Define path of excel sheet
			String excelFilePath = "F:\\SeeniumRead.xlsx";
			FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

			ArrayList<String> itemList = new ArrayList<String>();

			Workbook workbook = new XSSFWorkbook(inputStream);
			Sheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = firstSheet.iterator();

			while (iterator.hasNext()) {
				Row nextRow = iterator.next();
				if (nextRow.getRowNum() != 0) {
					Iterator<Cell> cellIterator = nextRow.cellIterator();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						cell.setCellType(Cell.CELL_TYPE_STRING);
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_STRING:
							System.out.print("String : " + cell.getStringCellValue());
							itemList.add(cell.getStringCellValue());
							break;
						case Cell.CELL_TYPE_BOOLEAN:
							System.out.print(cell.getBooleanCellValue());
							break;
						case Cell.CELL_TYPE_NUMERIC:
							cell.setCellType(Cell.CELL_TYPE_NUMERIC);
							System.out.println("Numeric : " + cell.getNumericCellValue());
							String valueInt = String.valueOf(cell.getNumericCellValue());
							itemList.add(valueInt);
						}
						System.out.print("  ");
					}
				}
			}
			workbook.close();
			inputStream.close();

			// Print the values
			for (String arrList : itemList) {
				System.out.println(arrList);
			}
			return itemList;

		} catch (Exception ex) {
			logger.error("Exception in readExcel method of Browser class : " + ex);
		}
		return null;
	}

	// Upload the file
	public void uploadFile(WebElement element, String filepath) {
		try {
			element.sendKeys(filepath);
		} catch (Exception ex) {
			logger.error("Exception in uploadFile method of Browser class : " + ex);
		}
	}

	// Scroll to bottom page using javascript
	public void scrollDownBottomPage(WebDriver driver) {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	// Scroll upto element using javascript
	public void scrollDownUptoelement(WebDriver driver, WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
	}

	public static void takeSnapShot(WebDriver webdriver) throws Exception {
		try {
			String path = "";
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
			Calendar cal = Calendar.getInstance();
			String timeStamp = dateFormat.format(cal.getTime());

			// Convert web driver object to TakeScreenshot
			TakesScreenshot scrShot = ((TakesScreenshot) webdriver);

			// Call getScreenshotAs method to create image file
			File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);

			path = new StringBuilder().append(base_path).append(" \\target\\Screenshot ").append("\\").toString();
			// Move image file to new destination
			snapshotPath = base_path + "\\target\\Screenshot\\Fail" + timeStamp + ".jpg";
			File DestFile = new File(snapshotPath);

			// Copy file at destination
			FileUtils.copyFile(SrcFile, DestFile);
			
			//Add screenshot in report
			addScreenshotReport();
		} catch (Exception ex) {
			logger.error(
					new StringBuilder().append("Failed to capture screenshot: ").append(ex.getMessage()).toString());
		}

	}
	
	public static void addScreenshotReport() throws IOException{
		Reporter.addScreenCaptureFromPath(snapshotPath);
	}
}

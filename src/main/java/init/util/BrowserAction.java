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
import org.openqa.selenium.StaleElementReferenceException;
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
		 //PropertyConfigurator.configure("//Properties//log4j.properties");
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
	public boolean verifyElementDisplayed(WebDriver driver, By locator) throws Exception {
		try {
			if (getElement(driver, locator).isDisplayed()) {
				logger.info("viewElementDisplayed with locator : " + locator);
				return true;
			} else {
				logger.info("viewElement not displayed with locator : " + locator);
				return false;
			}
		} catch (NoSuchElementException ex) {
			takeSnapShot(driver);
			reportLogs("NoSuchElementException in viewElementDisplayed method", ex.toString());
		} catch (Exception ex) {
			takeSnapShot(driver);
			reportLogs("Exception in viewElementDisplayed method of BrowserActions class" , ex.toString());
		}
		return false;
	}

	// Wait for element
	public void elementWait(int timeout) throws InterruptedException {
		try {
			Thread.sleep(timeout);
			logger.info("Wait for element upto " + timeout + " Seconds ");
		} catch (TimeoutException ex) {
			reportLogs("Timeout Exception in elementWait method of BrowserActions class" , ex.toString());
		} catch (Exception ex) {
			reportLogs("Exception in elementWait method of BrowserActions class <{}>" , ex.toString());
		}
	}

	// Wait to load the page
	public boolean waitForPageLoad(WebDriver driver, By locator) throws Exception {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

			logger.info("waitForPageLoad with locator : " + locator);

			if (element != null)
				return true;
		} catch (NoSuchElementException ex) {
			takeSnapShot(driver);
			reportLogs("NoSuchElementException in waitForPageLoad method  of BrowserActions class", ex.toString());
		} catch (TimeoutException ex) {
			takeSnapShot(driver);
			reportLogs("Timeout Exception in waitForPageLoad method of BrowserActions class", ex.toString());
		} catch (Exception ex) {
			takeSnapShot(driver);
			reportLogs("Exception in waitForPageLoad method of BrowserActions class", ex.toString());
		}
		return false;
	}
	
	public void reportLogs(String message,String ex){
		Reporter.addStepLog(message);
		Reporter.setTestRunnerOutput(message + "<{}> " + ex);
		logger.error(message + "<{}> " + ex);
	
	}

	// To click element
	public void clickButton(WebDriver driver, By locator) throws Exception {
		try {
			WebElement element = getElement(driver, locator);
			element.click();
			logger.info("clickButton with locator : " + locator);
		} catch (Exception ex) {
			takeSnapShot(driver);
			reportLogs("Exception in clickButton method of BrowserActions class" , ex.toString());
			throw new Exception();
			
		}
	}

	// Get text from page
	public String getTxt(WebDriver driver, By locator) throws Exception {
		try {
			WebElement element = getElement(driver, locator);
			String text = element.getText();
			logger.info("Get text with locator : " + locator);
			return text;
		} catch (Exception ex) {
			takeSnapShot(driver);
			reportLogs("Exception in getTxt method of BrowserActions class" , ex.toString());
			throw new Exception();
		}
	}

	// To enter text in text box
	public void enterText(WebDriver driver, By locator, String enterText) throws Exception {
		try {
			WebElement element = getElement(driver, locator);
			element.sendKeys(enterText);
			logger.info("Enter text with locator : " + locator);
		} catch (NoSuchElementException ex) {
			takeSnapShot(driver);
			reportLogs("NoSuchElementException in enterText method  of BrowserActions class" , ex.toString());
		} catch (Exception ex) {
			takeSnapShot(driver);
			reportLogs("Exception in enterText method of BrowserActions class" , ex.toString());
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
	public WebElement getElement(WebDriver driver, By locator) throws Exception {
		try {
			return driver.findElement(locator);
		} catch (NoSuchElementException ex) {
			logger.error("NoSuchElementException in getElement method of BrowserActions class" +ex);
			throw new NoSuchElementException();
		}  catch (Exception ex) {
			logger.error("Exception in getElement method of BrowserActions class" +ex);
			throw new Exception();
		}
	}

	// Hover and submenu
	public void hoverToSubmenu(WebDriver driver, By locator) throws Exception{
		try {
			WebElement element = getElement(driver, locator);
			Actions action = new Actions(driver);
			action.moveToElement(element).build().perform();
			action.moveToElement(element).click().build().perform();
			logger.info("hoverToSubmenu with locator : " + element);
		} catch (Exception ex) {
			takeSnapShot(driver);
			reportLogs("Exception in hoverToSubmenu method of BrowserActions class" , ex.toString());
		}
	}

	// Wait upto loading indicator is displaying
	public void loadingIndicator(WebDriver driver, By locator) throws Exception {
		try {
			WebElement element = getElement(driver, locator);
			for (int i = 1; i <= 60; i++) {
				if (element.isDisplayed() == true) {
					logger.info("Loading indicator is displaying");
					Thread.sleep(1000);
				} else {
					logger.info("loading indicator is displaying");
					break;
				}
			}
		} catch (Exception ex) {
			takeSnapShot(driver);
			reportLogs("Exception in loadingIndicator method of BrowserActions class" , ex.toString());
		}
	}

	// select value from dropdown
	public void dropDownValue(WebDriver driver, By locator, String selectValue) throws Exception {
		try {
			WebElement elementDropDown = getElement(driver, locator);
			Select ddp = new Select(elementDropDown);
			ddp.selectByVisibleText(selectValue);
			logger.info("Value selected from dropdown");
		} catch (Exception ex) {
			takeSnapShot(driver);
			reportLogs("Exception in dropDownValue method of BrowserActions class" , ex.toString());
		}
	}

	// To delete characters from string and return it.
	public String trimString(String oginalString, int startIndex, int enfIndex) {
		try {
			StringBuffer sb = new StringBuffer(oginalString);
			sb.delete(startIndex, enfIndex);
			return sb.toString();
		} catch (Exception ex) {
			reportLogs("Exception in trimString method of BrowserActions class" , ex.toString());
		}
		return null;
	}

	// Read the excel and return values in ArrayList<>
	public ArrayList<String> readExcel() throws IOException {
		try {
			// Define path of excel sheet
			String excelFilePath = base_path+"\\TestData.xlsx";
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
			reportLogs("Exception in readExcel method of Browser class" , ex.toString());
		}
		return null;
	}

	// Upload the file
	public void uploadFile(WebDriver driver, By locator, String filepath) {
		try {
			WebElement element = getElement(driver, locator);
			element.sendKeys(filepath);
		} catch (Exception ex) {
			reportLogs("Exception in uploadFile method of Browser class" , ex.toString());
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

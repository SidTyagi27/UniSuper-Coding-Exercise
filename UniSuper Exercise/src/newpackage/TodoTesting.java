package newpackage;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;


public class TodoTesting 
{   
	private static WebDriver driver;
	
	@BeforeClass
	public static void setUp()
	{
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Sid\\Desktop\\selenium-java-3.0.0\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://todomvc.com");
		WebElement element = driver.findElement(By.linkText("AngularJS")); //navigates to AngularJS page
		element.click();
	}
	
	/* Add an item to list and edit its content */
	@Test
	public void addEditTodo()
	{
		//Add an item to the list
		WebElement element = driver.findElement(By.cssSelector("#new-todo"));
		element.click();
		element.sendKeys("List item one");
		element.submit();
		
		//Check if the item has been added
		WebElement ItemOne = driver.findElement(By.xpath("//*[@id='todo-list']/li[1]/div/label"));
		String ItemOneText = ItemOne.getText();
		Assert.assertEquals("List item one", ItemOneText);
		
		//Edit the item's text and check the edit
		Actions action = new Actions(driver);
		action.doubleClick(ItemOne).perform();
		ItemOne.sendKeys(" [Edited]");
		ItemOne.submit();
		String ItemOneTextEdited = ItemOne.getText();
		Assert.assertEquals("List item one [Edited]", ItemOneTextEdited);
	}
	
	/*Complete a to-do then re-activate it */
	@Test
	public void completeReactivateTodo()
	{
		//Add an item to the list
		WebElement element = driver.findElement(By.cssSelector("#new-todo"));
		element.click();
		element.sendKeys("List item one");
		element.submit();
		
		//Click the circle to complete the todo item
		WebElement IncompleteCircle = driver.findElement(By.cssSelector("#todo-list > li:nth-child(1) > div > input"));
		IncompleteCircle.click();
		WebElement CompleteCircle = driver.findElement(By.cssSelector("#todo-list > li.ng-scope.completed > div > input"));
		Assert.assertNotEquals(IncompleteCircle, CompleteCircle);
		
		//Re-activate the item
		CompleteCircle.click();
		Assert.assertNotEquals(CompleteCircle, IncompleteCircle);
	}
	
	/* Add two to-dos and complete them all */
	@Test
	public void secondTodoCompleteAll()
	{
		//Add two items to the list
		WebElement element = driver.findElement(By.cssSelector("#new-todo"));
		element.click();
		element.sendKeys("List item one");
		element.submit();	
		element.click();
		element.sendKeys("List item two");
		element.submit();
		
		//Check item two since we know item one works from previous tests
		WebElement ItemTwo = driver.findElement(By.xpath("//*[@id='todo-list']/li[2]/div/label"));
		String ItemTwoText = ItemTwo.getText();
		Assert.assertEquals("List item two", ItemTwoText);
		
		WebElement MarkAllUnchecked = driver.findElement(By.cssSelector("#toggle-all"));
		MarkAllUnchecked.click();
		
		WebElement itemTwoComplete = driver.findElement(By.xpath("//*[@id='todo-list']/li[2]"));
		Assert.assertNotEquals(ItemTwo, itemTwoComplete);
	}
	
	/* Filter to-dos based on their completion status*/
	@Test 
	public void filterTodos()
	{
		//Add two items to the list
		WebElement element = driver.findElement(By.cssSelector("#new-todo"));
		element.click();
		element.sendKeys("List item one");
		element.submit();	
		element.click();
		element.sendKeys("List item two");
		element.submit();
		
		//Complete one item then filter it out
		WebElement IncompleteItemOne = driver.findElement(By.cssSelector("#todo-list > li:nth-child(1) > div > input"));
		IncompleteItemOne.click();
		WebElement filterCompleted = driver.findElement(By.cssSelector("#filters > li:nth-child(3) > a"));
		filterCompleted.click();
		WebElement itemOneComplete = driver.findElement(By.xpath("//*[@id='todo-list']/li[1]"));
		Assert.assertNotEquals(IncompleteItemOne, itemOneComplete);
	}
	
	/* clear one or all completed to-dos */
	@Test
	public void clearOneAndAllTodos()
	{
		//Add three items to the list
		WebElement element = driver.findElement(By.cssSelector("#new-todo"));
		element.click();
		element.sendKeys("List item one");
		element.submit();	
		element.click();
		element.sendKeys("List item two");
		element.submit();
		element.click();
		element.sendKeys("List item three");
		element.submit();
		
		//Clear one item
		WebElement ItemOne = driver.findElement(By.xpath("//*[@id='todo-list']/li[1]/div/label"));
		WebElement ClearItemOne = driver.findElement(By.cssSelector("#todo-list > li:nth-child(2) > div > button"));
		ClearItemOne.click();
		Assert.assertNull(ItemOne);
		
		//Clear all completed items
		WebElement ItemTwo = driver.findElement(By.xpath("//*[@id='todo-list']/li[2]/div/label"));
		WebElement ItemThree = driver.findElement(By.xpath("//*[@id='todo-list']/li[3]/div/label"));
		
		WebElement IncompleteItemTwo = driver.findElement(By.cssSelector("#todo-list > li:nth-child(2) > div > input"));
		IncompleteItemTwo.click();
		WebElement IncompleteItemThree = driver.findElement(By.cssSelector("#todo-list > li:nth-child(2) > div > input"));
		IncompleteItemThree.click();
		
		WebElement ClearAllCompleted = driver.findElement(By.cssSelector("#clear-completed"));
		ClearAllCompleted.click();
		Assert.assertNull(ItemTwo);
		Assert.assertNull(ItemThree);
	}
    
	@AfterClass
	public static void endSession()
	{
        if (driver != null) 
        {
            driver.close();
            driver.quit();
        }
	}
 }
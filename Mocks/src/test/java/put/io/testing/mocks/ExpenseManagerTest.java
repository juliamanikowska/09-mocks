package put.io.testing.mocks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import put.io.students.fancylibrary.database.IFancyDatabase;
import put.io.students.fancylibrary.service.FancyService;

public class ExpenseManagerTest {

    @Test
    public void testCalculateTotal(){
        ExpenseRepository mockObject = mock(ExpenseRepository.class);
        when(mockObject.getExpenses()).thenReturn(Collections.nCopies(3,new Expense(2)));
        ExpenseManager test =  new ExpenseManager(mockObject,new FancyService());
        assertEquals(6,test.calculateTotal());
    }

    @Test
    public void testCalculateTotalForCategory(){
        ExpenseRepository mockObject = mock(ExpenseRepository.class);
        when(mockObject.getExpensesByCategory(anyString())).thenReturn(new ArrayList<>());
        when(mockObject.getExpensesByCategory("Home")).thenReturn(Collections.nCopies(3,new Expense(2)));
        when(mockObject.getExpensesByCategory("Car")).thenReturn(Collections.nCopies(2,new Expense(1)));
        ExpenseManager test =  new ExpenseManager(mockObject,new FancyService());
        assertEquals(0,test.calculateTotalForCategory("Cara"));

    }

    @Test
    public void testCalculateTotalInDollars() throws ConnectException{
        ExpenseRepository mockExpenseRepository = mock(ExpenseRepository.class);
        FancyService mockFancyService = mock(FancyService.class);
        when(mockExpenseRepository.getExpenses()).thenReturn(Collections.nCopies(2,new Expense(1)));
        when(mockFancyService.convert(anyDouble(), eq("PLN"), eq("USD"))).thenAnswer(
                new Answer() {
                    public Object answer(InvocationOnMock invocation) {
                        Object[] args = invocation.getArguments();
                        return invocation.getArgument(0, Double.class)*4 ;
                    }
                });
        ExpenseManager test =  new ExpenseManager(mockExpenseRepository,mockFancyService);
        assertEquals(8,test.calculateTotalInDollars());
    }

}

package put.io.testing.mocks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import static org.mockito.Mockito.*;

import put.io.students.fancylibrary.database.FancyDatabase;
import put.io.students.fancylibrary.database.IFancyDatabase;

public class ExpenseRepositoryTest {

    @Test
    void loadExpenses() {
        IFancyDatabase mockObject = mock(IFancyDatabase.class);
        when(mockObject.queryAll()).thenReturn(new ArrayList<>());
        ExpenseRepository test =  new ExpenseRepository(mockObject);
        test.loadExpenses();
        InOrder inOrder = inOrder(mockObject);
        inOrder.verify(mockObject).connect();
        inOrder.verify(mockObject).queryAll();
        inOrder.verify(mockObject).close();
        assertTrue(test.getExpenses().isEmpty());
    }

    @Test
    void testSaveExpenses() {
        IFancyDatabase mockObject = mock(IFancyDatabase.class);
        when(mockObject.queryAll()).thenReturn(new ArrayList<>());
        ExpenseRepository test =  new ExpenseRepository(mockObject);
        for(int i=0;i<5;i++)
            test.addExpense(new Expense());
        test.saveExpenses();
        verify(mockObject,times(5)).persist(any(Expense.class));
    }
}

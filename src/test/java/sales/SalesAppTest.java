package sales;

import org.junit.Test;

import static org.mockito.Mockito.*;

public class SalesAppTest {

	@Test
	public void testGenerateReport_givenSalesIdNull_thenGetSalesBySalesIdNotCarryOut() {

		SalesApp salesApp = new SalesApp();

		String salesId = null;
		SalesDao salesDao = mock(SalesDao.class);
		salesApp.generateSalesActivityReport(null, 1000, false, false);

		verify(salesDao, times(0)).getSalesBySalesId(salesId);
	}

}

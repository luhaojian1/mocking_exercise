package sales;

import org.junit.Test;

import java.util.Date;

import static org.mockito.Mockito.*;

public class SalesAppTest {

	@Test
	public void testGenerateSalesActivityReport_givenSalesIdNull_thenGetSalesBySalesIdNotCarryOut() {

		SalesApp salesApp = new SalesApp();

		String salesId = null;

		SalesDao salesDao = mock(SalesDao.class);
		salesApp.generateSalesActivityReport(salesId, 1000, false, false, salesDao, null);

		verify(salesDao, times(0)).getSalesBySalesId(salesId);
	}

	@Test
	public void testGenerateSalesActivityReport_givenSalesIdNotNullButDateIsNotValid_thenMehtodGetReportDataNotCarryOut() {

		SalesApp salesApp = new SalesApp();
		String salesId = "Array";


		SalesDao salesDao = mock(SalesDao.class);
		Sales msales = mock(Sales.class);
		when(salesDao.getSalesBySalesId(salesId)).thenReturn(msales);
		when(msales.getEffectiveFrom()).thenReturn(new Date());
		when(msales.getEffectiveTo()).thenReturn(new Date(System.currentTimeMillis()));

		SalesReportDao salesReportDao = mock(SalesReportDao.class);
		salesApp.generateSalesActivityReport(salesId, 1000, false, false, salesDao, salesReportDao);

		verify(salesReportDao, times(1)).getReportData(msales);
	}

    @Test
    public void testGenerateSalesActivityReport_givenSalesIdNotNullAndDateValid_thenMehtodGetReportDataCarryOut() {

        SalesApp salesApp = new SalesApp();
        String salesId = "Array";


        SalesDao salesDao = mock(SalesDao.class);
        Sales msales = mock(Sales.class);
        when(salesDao.getSalesBySalesId(salesId)).thenReturn(msales);
        when(msales.getEffectiveFrom()).thenReturn(new Date());
        when(msales.getEffectiveTo()).thenReturn(new Date(System.currentTimeMillis() + 1000));

        SalesReportDao salesReportDao = mock(SalesReportDao.class);
        salesApp.generateSalesActivityReport(salesId, 1000, false, false, salesDao, salesReportDao);

        verify(salesReportDao, times(1)).getReportData(msales);
    }
}

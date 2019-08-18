package sales;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SalesApp {

	public void generateSalesActivityReport(String salesId, int maxRow, boolean isNatTrade, boolean isSupervisor) {

        SalesDao salesDao = new SalesDao();
		SalesReportDao salesReportDao = new SalesReportDao();
		List<String> headers = null;

        if (salesId == null) {
			return;
		}

        Sales sales = salesDao.getSalesBySalesId(salesId);

        Date today = new Date();
		if (today.after(sales.getEffectiveTo())
				|| today.before(sales.getEffectiveFrom())){
			return;
		}

        List<SalesReportData> reportDataList = salesReportDao.getReportData(sales);

        if (isNatTrade) {
			headers = Arrays.asList("Sales ID", "Sales Name", "Activity", "Time");
		} else {
			headers = Arrays.asList("Sales ID", "Sales Name", "Activity", "Local Time");
		}

        SalesActivityReport report = this.generateReport(headers, reportDataList);

        EcmService ecmService = new EcmService();
		ecmService.uploadDocument(report.toXml());

    }

	private SalesActivityReport generateReport(List<String> headers, List<SalesReportData> reportDataList) {
		// TODO Auto-generated method stub
		return null;
	}

}

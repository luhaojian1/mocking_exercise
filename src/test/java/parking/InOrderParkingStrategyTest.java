package parking;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class InOrderParkingStrategyTest {

	@Test
    public void testCreateReceipt_givenACarAndAParkingLog_thenGiveAReceiptWithCarNameAndParkingLotName() {

	    /* Exercise 1, Write a test case on InOrderParkingStrategy.createReceipt()
	    * With using Mockito to mock the input parameter */

	    Car mockCar = mock(Car.class);
	    when(mockCar.getName()).thenReturn("LeoCar2");
	    ParkingLot mParkingLot = mock(ParkingLot.class);
	    when(mParkingLot.getName()).thenReturn("Parking Lot 2");

	    InOrderParkingStrategy inOrderParkingStrategy = new InOrderParkingStrategy();
	    Receipt receipt = inOrderParkingStrategy.createReceipt(mParkingLot, mockCar);

	    verify(mockCar,times(1)).getName();
	    verify(mParkingLot).getName();

	    assertEquals("LeoCar2", receipt.getCarName());
	    assertEquals("Parking Lot 2", receipt.getParkingLotName());


    }

    @Test
    public void testCreateNoSpaceReceipt_givenACar_thenGiveANoSpaceReceipt() {

        /* Exercise 1, Write a test case on InOrderParkingStrategy.createNoSpaceReceipt()
         * With using Mockito to mock the input parameter */
        Car mockCar = mock(Car.class);
        when(mockCar.getName()).thenReturn("LeoCar2");

        InOrderParkingStrategy inOrderParkingStrategy = new InOrderParkingStrategy();
        Receipt receipt = inOrderParkingStrategy.createNoSpaceReceipt(mockCar);

        verify(mockCar,times(1)).getName();

        assertEquals("LeoCar2", receipt.getCarName());
        assertEquals(ParkingStrategy.NO_PARKING_LOT, receipt.getParkingLotName());

    }

    @Test
    public void testPark_givenNoAvailableParkingLot_thenCreateNoSpaceReceipt(){

	    /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for no available parking lot */
        Car car = new Car("LeoCar");

        InOrderParkingStrategy inOrderParkingStrategy = spy(new InOrderParkingStrategy());
        Receipt receipt = inOrderParkingStrategy.park(null, car);

        verify(inOrderParkingStrategy,times(1)).createNoSpaceReceipt(car);

    }

    @Test
    public void testPark_givenThereIsOneParkingLotWithSpace_thenCreateReceipt(){

        /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for one available parking lot */
        ParkingLot parkingLot = new ParkingLot("Parking Lot", 1);
        List<ParkingLot> parkingLots = Arrays.asList(parkingLot);

        Car car = new Car("LeoCar");

        InOrderParkingStrategy inOrderParkingStrategy = spy(new InOrderParkingStrategy());
        Receipt receipt = inOrderParkingStrategy.park(parkingLots, car);

        verify(inOrderParkingStrategy,times(1)).createReceipt(parkingLot, car);
    }

    @Test
    public void testPark_givenThereIsOneFullParkingLot_thenCreateReceipt(){

        /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for one available parking lot but it is full */

        ParkingLot parkingLot = new ParkingLot("Parking Lot", 1);

        Car car = new Car("LeoCar");
        parkingLot.getParkedCars().add(car);
        List<ParkingLot> parkingLots = Arrays.asList(parkingLot);

        InOrderParkingStrategy inOrderParkingStrategy = spy(new InOrderParkingStrategy());
        Receipt receipt = inOrderParkingStrategy.park(parkingLots, car);

        verify(inOrderParkingStrategy, times(0)).createReceipt(parkingLot,car);
        verify(inOrderParkingStrategy).createNoSpaceReceipt(car);


    }

    @Test
    public void testPark_givenThereIsMultipleParkingLotAndFirstOneIsFull_thenCreateReceiptWithUnfullParkingLot(){

        /* Exercise 3: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for multiple parking lot situation */
        ParkingLot parkingLot = new ParkingLot("Parking Lot", 1);
        ParkingLot parkingLot2 = new ParkingLot("Parking Lot 2", 1);
        parkingLot.getParkedCars().add(new Car("car2"));
        Car car = new Car("LeoCar");

        List<ParkingLot> parkingLots = Arrays.asList(parkingLot, parkingLot2);
        InOrderParkingStrategy inOrderParkingStrategy = spy(new InOrderParkingStrategy());
        Receipt receipt = inOrderParkingStrategy.park(parkingLots, car);

        verify(inOrderParkingStrategy,times(1)).createReceipt(parkingLot2, car);

        assertEquals("LeoCar", receipt.getCarName());
        assertEquals("Parking Lot 2", receipt.getParkingLotName());
    }


}

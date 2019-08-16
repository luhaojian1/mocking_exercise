package parking;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class VipParkingStrategyTest {

    @Test
    public void testPark_givenAVipCarAndAFullParkingLog_thenGiveAReceiptWithCarNameAndParkingLotName() {

        /* Exercise 4, Write a test case on VipParkingStrategy.park()
         * With using Mockito spy, verify and doReturn */

        Car car = new Car("carA");
        ParkingLot parkingLot = new ParkingLot("Parking Lot", 1);
        parkingLot.getParkedCars().add(new Car("car"));
        List<ParkingLot> parkingLots = Arrays.asList(parkingLot);

        VipParkingStrategy vipParkingStrategy = spy(VipParkingStrategy.class);
        when(vipParkingStrategy.isAllowOverPark(car)).thenReturn(true);

        Receipt receipt = vipParkingStrategy.park(parkingLots, car);

        assertEquals("carA", receipt.getCarName());
        assertEquals("Parking Lot", receipt.getParkingLotName());
    }

    @Test
    public void testPark_givenCarIsNotVipAndAFullParkingLog_thenGiveNoSpaceReceipt() {

        /* Exercise 4, Write a test case on VipParkingStrategy.park()
         * With using Mockito spy, verify and doReturn */
        Car car = new Car("car");
        ParkingLot parkingLot = new ParkingLot("Parking Lot", 1);
        parkingLot.getParkedCars().add(new Car("car"));
        List<ParkingLot> parkingLots = Arrays.asList(parkingLot);

        VipParkingStrategy vipParkingStrategy = spy(VipParkingStrategy.class);
        when(vipParkingStrategy.isAllowOverPark(car)).thenReturn(false);

        Receipt receipt = vipParkingStrategy.park(parkingLots, car);

        verify(vipParkingStrategy, times(0)).createReceipt(parkingLot, car);
        assertEquals("car", receipt.getCarName());
        assertEquals("No Parking Lot", receipt.getParkingLotName());
    }

    @Test
    public void testIsAllowOverPark_givenCarNameContainsCharacterAAndIsVipCar_thenReturnTrue() {

        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
        Car car = new Car("LeoCarA");

        VipParkingStrategy vipParkingStrategy = spy(new VipParkingStrategy());

        CarDao carDao = mock(CarDao.class);
        when(vipParkingStrategy.getCarDao()).thenReturn(carDao);
        when(carDao.isVip("LeoCarA")).thenReturn(true);

        boolean allowOverPark = vipParkingStrategy.isAllowOverPark(car);
        assertTrue(allowOverPark);
    }

    @Test
    public void testIsAllowOverPark_givenCarNameDoesNotContainsCharacterAAndIsVipCar_thenReturnFalse() {

        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
        Car car = new Car("LeoCar");

        VipParkingStrategy vipParkingStrategy = spy(new VipParkingStrategy());

        CarDao carDao = mock(CarDao.class);

        when(vipParkingStrategy.getCarDao()).thenReturn(carDao);
        when(carDao.isVip("LeoCar")).thenReturn(true);

        boolean allowOverPark = vipParkingStrategy.isAllowOverPark(car);
        assertFalse(allowOverPark);
    }

    @Test
    public void testIsAllowOverPark_givenCarNameContainsCharacterAAndIsNotVipCar_thenReturnFalse() {
        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
        Car car = new Car("LeoCarA");

        VipParkingStrategy vipParkingStrategy = spy(VipParkingStrategy.class);

        CarDao carDao = mock(CarDao.class);

        when(vipParkingStrategy.getCarDao()).thenReturn(carDao);
        when(carDao.isVip("LeoCarA")).thenReturn(false);

        boolean allowOverPark = vipParkingStrategy.isAllowOverPark(car);
        assertFalse(allowOverPark);
    }

    @Test
    public void testIsAllowOverPark_givenCarNameDoesNotContainsCharacterAAndIsNotVipCar_thenReturnFalse() {
        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
        Car car = new Car("LeoCar");

        VipParkingStrategy vipParkingStrategy = spy(VipParkingStrategy.class);

        CarDao carDao = mock(CarDao.class);
        when(vipParkingStrategy.getCarDao()).thenReturn(carDao);
        when(carDao.isVip("LeoCar")).thenReturn(false);

        boolean allowOverPark = vipParkingStrategy.isAllowOverPark(car);
        assertFalse(allowOverPark);
    }

    private Car createMockCar(String carName) {
        Car car = mock(Car.class);
        when(car.getName()).thenReturn(carName);
        return car;
    }
}

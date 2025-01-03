package MovieReservation;

public interface DiscountCondition {
    boolean isSatisfiedBy(Screening screening);
}


package com.demeatrix.VaciCure.enums;

/**
 * Type of stock movement in the inventory ledger.
 */
public enum StockMovementType {
    RECEIVED,
    ADMINISTERED,
    WASTED_EXPIRED,
    WASTED_VIAL_OPENED,
    WASTED_COLD_CHAIN_BREAK,
    WASTED_CONTAMINATION,
    TRANSFERRED_OUT,
    TRANSFERRED_IN,
    ADJUSTMENT
}

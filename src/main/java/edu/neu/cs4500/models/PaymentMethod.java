package edu.neu.cs4500.models;

public enum PaymentMethod {
    CC {
        @Override
        public String toString() {
            return "Credit Card";
        }
    },
    CASH {
        @Override
        public String toString() {
            return "Cash";
        }
    },
    CHECK {
        @Override
        public String toString() {
            return "Check";
        }
    },
    VENMO {
        @Override
        public String toString() {
            return "Venmo";
        }
    },
    PAYPAL {
        @Override
        public String toString() {
            return "Paypal";
        }
    },
    SQUARE {
        @Override
        public String toString() {
            return "Square";
        }
    };

    public static boolean contains(String s) {
        for (PaymentMethod p: PaymentMethod.values()) {
            if (p.toString().toLowerCase().equals(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}


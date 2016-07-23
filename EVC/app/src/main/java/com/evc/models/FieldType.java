package com.evc.models;

public enum FieldType{
    FIRST_NAME,
    LAST_NAME,
    EMAIL,
    PHONE,
    LOGO,
    BACKGROUND,
    ADDRESS,
    WEBSITE,
    NOTHING;

    public static FieldType getFieldType(int field_name_id) {

        switch (field_name_id) {
            case 1: {
                return FieldType.ADDRESS;
            }
            case 2: {
                return FieldType.BACKGROUND;
            }

            case 3: {
                return FieldType.EMAIL;
            }
            case 4: {
                return FieldType.FIRST_NAME;
            }

            case 5: {
                return FieldType.LAST_NAME;
            }

            case 6: {
                return FieldType.LOGO;
            }

            case 7: {
                return FieldType.PHONE;
            }

            case 8: {
                return FieldType.WEBSITE;
            }
        }

        return FieldType.NOTHING;
    }

    public static int getFieldTypeid(FieldType type) {
        switch (type) {
            case ADDRESS: return 1;
            case BACKGROUND: return 2;
            case EMAIL: return 3;
            case FIRST_NAME: return 4;
            case LAST_NAME: return 5;
            case LOGO: return 6;
            case PHONE: return 7;
            case WEBSITE: return 8;
        }

        return 0;
    }
}
package com.nmd.enums;

public enum VehicleType {
    SCOOTER(1, 'S'),
    CAR(2, 'C'),
    BUS(3,'B');

    private Integer id;
    private Character specification;

    VehicleType(Integer id, Character specification) {
        this.id = id;
        this.specification = specification;
    }

    public static boolean isValid(VehicleType vehicleType) {
        return VehicleType.getById(vehicleType.getId()) !=null;
    }

    public Integer getId() {
        return id;
    }

    public Character getSpecification() {
        return specification;
    }

    public static VehicleType getById(Integer vehicleTypeId){
        for (VehicleType vehicleType : VehicleType.values()) {
            if(vehicleTypeId.equals(vehicleType.getId())){
                return vehicleType;
            }
        }
        return null;
    }
}

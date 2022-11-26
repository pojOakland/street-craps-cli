package com.benoakland.streetcrapscli.dto;

public class PlayerUpdateDto {
    // Properties
    private int id;
    private int lifetimeBalanceUpdate;

    public PlayerUpdateDto() {
    }

    public PlayerUpdateDto(int id, int lifetimeBalanceUpdate) {
        this.id = id;
        this.lifetimeBalanceUpdate = lifetimeBalanceUpdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLifetimeBalanceUpdate() {
        return lifetimeBalanceUpdate;
    }

    public void setLifetimeBalanceUpdate(int lifetimeBalanceUpdate) {
        this.lifetimeBalanceUpdate = lifetimeBalanceUpdate;
    }
}

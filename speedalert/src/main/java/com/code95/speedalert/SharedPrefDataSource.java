package com.code95.speedalert;

public interface SharedPrefDataSource {
    void setLastAlertDate(Long date);

    Long getLastAlertDate();
}

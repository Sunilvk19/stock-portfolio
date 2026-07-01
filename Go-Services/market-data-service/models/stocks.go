package models

import "time"

type Stock struct {
	Id            uint      `gorm:"primaryKey;autoIncrement" json:"id"`
	Symbol        string    `gorm:"uniqueindex;type:varchar(10)" json:"symbol"`
	CompanyName   string    `gorm:"type:varchar(100);not null" json:"company_name"`
	CurrentPrice  float64   `gorm:"type:decimal(10,2);not null" json:"current_price"`
	PreviousClose float64   `gorm:"type:decimal(10,2)" json:"previous_close"`
	Volume        int64     `gorm:"type:bigint;default:0" json:"volume"`
	IsActive      bool      `gorm:"type:boolean;default:true" json:"is_active"`
	CreatedAt     time.Time `gorm:"autoCreateTime"`
	UpdatedAt     time.Time `gorm:"autoUpdateTime"`
}

package config

import (
	"fmt"
	"log"

	"gorm.io/driver/mysql"
	"gorm.io/gorm"
)
var db *gorm.DB
func ConnectDB(cfg *Config) {
	dsn := fmt.Sprintf("%s:%s@tcp([IP_ADDRESS])/%s?charset=utf8mb4&parseTime=True&loc=Local",
		cfg.DBUser, cfg.DBPassword, cfg.DBName)

	_, err := gorm.Open(mysql.Open(dsn), &gorm.Config{})
	if err != nil {
		log.Fatal("Failed to connect to database")
	}
	fmt.Println("Successfully connected to database")

}
package repository

import (
	"market-data-service/config"
	"market-data-service/models"
)

func GetStockBySymbol(symbol string) (models.Stock, error){
	var stock models.Stock
	err := config.DB.Where("symbol = ? AND is_active = ?", symbol, true).First(&stock).Error
	if err != nil {
		return models.Stock{}, err
	}
	return stock, nil
}

func GetCurrentPrice(symbol string) (models.Stock, error) {
	var stock []models.Stock
	err := config.DB.Where("symbol = ?",symbol).Select("symbol","current_price").Find(&stock).Error
	if err != nil {
		return models.Stock{}, err
	}
	return stock[0], nil
}
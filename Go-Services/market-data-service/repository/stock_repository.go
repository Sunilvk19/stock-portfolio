package repository

import (
	"market-data-service/config"
	"market-data-service/models"
)

func GetAllStock() ([]models.Stock, error){
	var stocks []models.Stock
	err := config.DB.Where("is_active = ?", true).Find(&stocks).Error
	if err != nil {
		return nil, err
	}
	return stocks, nil
}

func GetStockBySymbol(symbol string) (models.Stock, error){
	var stock models.Stock
	err := config.DB.Where("symbol = ? AND is_active = ?", symbol, true).First(&stock).Error
	if err != nil {
		return models.Stock{}, err
	}
	return stock, nil
}
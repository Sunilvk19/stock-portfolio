package service

import (
	"market-data-service/models"
	"market-data-service/repository"
)

func GetStockBySymbol(symbol string) (models.Stock, error){
	return repository.GetStockBySymbol(symbol)
}

func GetCurrentPrice(symbol string) (models.Stock, error){
	return repository.GetCurrentPrice(symbol)
}
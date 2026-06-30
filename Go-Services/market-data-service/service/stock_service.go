package service

import (
	"market-data-service/models"
	"market-data-service/repository"
)

func GetAllStock() ([]models.Stock, error){
    return repository.GetAllStock()
}

func GetStockBySymbol(symbol string) (models.Stock, error){
	return repository.GetStockBySymbol(symbol)
}
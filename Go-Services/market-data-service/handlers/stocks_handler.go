package handlers

import (
	"market-data-service/config"
	"market-data-service/models"
	"net/http"

	"github.com/gin-gonic/gin"
)

func GetStockHandler(c *gin.Context) {
	var stocks []models.Stock
	err := config.DB.Where("is_active = ?", true).Find(&stocks).Error
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{
			"Error":"Failed to fetch from database",
		})
		return
	}
	c.JSON(http.StatusFound, gin.H{
		"Message":"Found",
		"Data":stocks,
	})	
}

func GetStockBySymbol(c *gin.Context) {
	var stock models.Stock
	symbol := c.Param("symbol")
	err := config.DB.Where("symbol = ? AND is_active = ?", symbol, true).First(&stock).Error
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{
			"Error" : "Failed to fetch by symbol",
		})
		return
	}
	c.JSON(http.StatusFound, gin.H{
		"Message":"Found",
		"Data":stock,
	})
}

func GetCurrentPrice(c *gin.Context) {
	var stock []models.Stock
	symbol := c.Param("symbol")
	err := config.DB.Where("symbol = ?", symbol).Select("symbol","current_price").Find(&stock).Error
	if err != nil {
		c.JSON(http.StatusNotFound, gin.H{
			"Error" : "Failed to fetch price",
		})
		return
	}
	c.JSON(http.StatusFound, gin.H{
		"Message" : "Found",
		"Data" : stock,
	})
}


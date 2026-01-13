package utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum ExcelUtil
{
	;

	private static final Logger log = LoggerFactory.getLogger(ExcelUtil.class);
	
	public static String getStringCellValue(Cell cell)
	{
		if(cell == null || cell.getCellType() == CellType.BLANK || cell.getCellType() == CellType._NONE)
		{
			return null;
		}
		CellType cellType = cell.getCellType();
		if(cellType == CellType.STRING)
		{
			return cell.getStringCellValue();
		}
		if(cellType == CellType.NUMERIC)
		{
			return String.valueOf(cell.getNumericCellValue());
		}
		log.error("未识别的单元格格式: {}", cell.getCellType().name());
		return null;
	}


	public static String getStringCellValueSpecial(Cell cell)
	{
		if(cell == null || cell.getCellType() == CellType.BLANK || cell.getCellType() == CellType._NONE)
		{
			return null;
		}
		CellType cellType = cell.getCellType();
		if(cellType == CellType.STRING)
		{
			String stringCellValue = cell.getStringCellValue();
			if(stringCellValue.contains("'"))
			{
				stringCellValue =stringCellValue.replace("'","");
			}
			return stringCellValue;
		}
		if(cellType == CellType.NUMERIC)
		{
			return String.valueOf(cell.getNumericCellValue());
		}
		log.error("未识别的单元格格式: {}", cell.getCellType().name());
		return null;
	}
}

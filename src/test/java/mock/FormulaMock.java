package mock;

import utils.ExcelUtil;
import utils.ResourceUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class FormulaMock
{
	private static final Logger log = LoggerFactory.getLogger(FormulaMock.class);

	private static final int START_ROW = 1;
	private static final int END_COL = 2;

	public static Map<String,String> load()
	{
		Map<String,String> formulaMap = new HashMap<>();

		try
		{
			InputStream inputStream = ResourceUtil.getResourceAsStream("excel/FormulaOfParam.xlsx");
			Workbook workbook = WorkbookFactory.create(inputStream);
			Sheet sheet = workbook.getSheetAt(0);
			int endRow = sheet.getLastRowNum();

			for (int i = START_ROW; i <= endRow; i++)
			{
				Row row = sheet.getRow(i);
				if (row == null)
				{
					continue;
				}
				if (row.getLastCellNum() < END_COL)
				{
					continue;
				}

				String cell0 = ExcelUtil.getStringCellValue(row.getCell(0)).trim();
				String cell1 = ExcelUtil.getStringCellValue(row.getCell(1)).trim();

				if (cell0.length()==0 || cell1.length()==0)
				{
					log.info("FormulaOfParam excel 有空数据");
					continue;
				}

				formulaMap.put(cell0,cell1);
			}
			workbook.close();
		}
		catch (Exception e)
		{
			log.error("读取原始点数据出错：", e.getMessage());
		}

		return formulaMap;
	}
}

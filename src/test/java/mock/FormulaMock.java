package mock;

import com.liyz.common.calculate.utils.FormulaUtil;
import utils.ExcelUtil;
import utils.ResourceUtil;
import entity.FormulaTest;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FormulaMock
{

	private static final Logger log = LoggerFactory.getLogger(FormulaMock.class);

	private static final int START_ROW = 1;
	private static final int END_COL = 2;

	public static List<FormulaTest> load() throws EncryptedDocumentException, IOException
	{
		List<FormulaTest> calculates = new ArrayList<>();

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

			String cell0 = ExcelUtil.getStringCellValue(row.getCell(0));
			String cell1 = ExcelUtil.getStringCellValue(row.getCell(1));

			if (cell0.length()==0 || cell1.length()==0)
			{
				log.info("FormulaExpression excel 有空数据");
				continue;
			}

			FormulaTest calculate = new FormulaTest();
			calculate.setFormulaCode(cell0);
			calculate.setFormulaExpression(cell1);
			calculates.add(calculate);
		}
		FormulaUtil.sort(calculates);
		workbook.close();
		return calculates;
	}
}

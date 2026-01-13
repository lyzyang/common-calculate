package mock;

import utils.ExcelUtil;
import utils.ResourceUtil;
import entity.PointTest;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PointMock
{

    private static final Logger log = LoggerFactory.getLogger(PointMock.class);

    private static final int START_ROW = 1;
    private static final int END_COL = 2;

    public static List<PointTest> load() throws EncryptedDocumentException, IOException
    {
        List<PointTest> pointOfParamList = new ArrayList<>();

        try(InputStream inputStream = ResourceUtil.getResourceAsStream("excel/PointOfParam.xlsx"); Workbook workbook = WorkbookFactory.create(inputStream))
        {
            Sheet sheet = workbook.getSheetAt(0);
            int endRow = sheet.getLastRowNum();
            Set<String> existOfPointCodeSet = new HashSet<>(1024);
            for (int i = START_ROW; i <= endRow; i++)
            {
                Row row = sheet.getRow(i);
                if(row == null)
                {
                    continue;
                }
                if(row.getLastCellNum() < END_COL)
                {
                    continue;
                }

                String cell0 = ExcelUtil.getStringCellValue(row.getCell(0));
                String cell1 = ExcelUtil.getStringCellValue(row.getCell(1));

                if(cell0.length()==0 || cell1.length() ==0)
                {
                    continue;
                }

                PointTest point = new PointTest();
                point.setPointCode(cell0.trim());
                Double tmp = new Double(cell1);
                point.setPointValue(tmp);

                pointOfParamList.add(point);
                existOfPointCodeSet.add(cell1);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        return pointOfParamList;
    }
}

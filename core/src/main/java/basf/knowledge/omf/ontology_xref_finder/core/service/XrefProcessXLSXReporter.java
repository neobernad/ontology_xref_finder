package basf.knowledge.omf.ontology_xref_finder.core.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import basf.knowledge.omf.ontology_xref_finder.core.model.ReportItem;
import basf.knowledge.omf.ontology_xref_finder.core.model.ReportItemXrefMatch;


public class XrefProcessXLSXReporter extends XrefProcessAbstractReporter {
	private static final Logger LOGGER = Logger.getLogger(XrefProcessXLSXReporter.class.getName());

	public XrefProcessXLSXReporter(String outputDirectory) {
		super(outputDirectory);
	}

	public void getReport() throws IOException {
		Workbook workbook = new XSSFWorkbook();
		CellStyle headerStyle = workbook.createCellStyle();
		CellStyle dataStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerStyle.setBorderBottom(CellStyle.BORDER_THIN);
		headerStyle.setBorderTop(CellStyle.BORDER_THIN);
		headerStyle.setBorderRight(CellStyle.BORDER_THIN);
		headerStyle.setBorderLeft(CellStyle.BORDER_THIN);
		dataStyle.setBorderBottom(CellStyle.BORDER_THIN);
		dataStyle.setBorderTop(CellStyle.BORDER_THIN);
		dataStyle.setBorderRight(CellStyle.BORDER_THIN);
		dataStyle.setBorderLeft(CellStyle.BORDER_THIN);
		
		if (!xrefFound.isEmpty()) {
			org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Mapped terms");
			sheet.setColumnWidth(0, 2000);
			sheet.setColumnWidth(1, 3000);
			sheet.setColumnWidth(2, 4000);
			sheet.setColumnWidth(3, 4000);
			sheet.setColumnWidth(4, 4000);
			
			int rowNum =  0;
			
			Row headerRow = sheet.createRow(rowNum);
			Cell headerCell = headerRow.createCell(0);
			headerCell.setCellValue("label");
			headerCell.setCellStyle(headerStyle);
			headerCell = headerRow.createCell(1);
			headerCell.setCellValue("definition");
			headerCell.setCellStyle(headerStyle);
			headerCell = headerRow.createCell(2);
			headerCell.setCellValue("xref uri");
			headerCell.setCellStyle(headerStyle);
			headerCell = headerRow.createCell(3);
			headerCell.setCellValue("xref definition");
			headerCell.setCellStyle(headerStyle);
			headerCell = headerRow.createCell(4);
			headerCell.setCellValue("xref synonyms");
			headerCell.setCellStyle(headerStyle);
			headerCell = headerRow.createCell(5); // Extra cell so that the last cell won't overflow
			headerCell.setCellValue("");
			
			rowNum += 1;
			
			for (ReportItemXrefMatch reportItem : xrefFound) {
				Row dataRow = sheet.createRow(rowNum);
				Cell dataCell = dataRow.createCell(0);
				dataCell.setCellValue(reportItem.getInputLabel());
				dataCell.setCellStyle(dataStyle);
				dataCell = dataRow.createCell(1);
				dataCell.setCellValue(reportItem.getInputDef());
				dataCell.setCellStyle(dataStyle);
				dataCell = dataRow.createCell(2);
				dataCell.setCellValue(reportItem.getXrefIri().getIRIString());
				dataCell.setCellStyle(dataStyle);
				dataCell = dataRow.createCell(3);
				String definitionsListStr = reportItem.getxRefDefinitions().stream().map(str -> str.toString()).collect(Collectors.joining(","));
				dataCell.setCellValue(definitionsListStr);
				dataCell.setCellStyle(dataStyle);
				dataCell = dataRow.createCell(4);
				String synonymListStr = reportItem.getxRefSynonymLabels().stream().map(str -> str.toString()).collect(Collectors.joining(","));
				dataCell.setCellValue(synonymListStr);
				dataCell.setCellStyle(dataStyle);
				dataCell = dataRow.createCell(5);
				dataCell.setCellValue("");
				rowNum += 1;
			}
		}
		if (!noXrefFound.isEmpty()) {
			org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Unmapped terms");
			sheet.setColumnWidth(0, 8000);
			
			int rowNum =  0;
			Row headerRow = sheet.createRow(rowNum);
			Cell headerCell = headerRow.createCell(0);
			headerCell.setCellValue("label");
			headerCell.setCellStyle(headerStyle);
			rowNum += 1;
			
			for (ReportItem reportItem : noXrefFound) {
				Row dataRow = sheet.createRow(rowNum);
				Cell dataCell = dataRow.createCell(0);
				dataCell.setCellValue(reportItem.getLabel());
				dataCell.setCellStyle(dataStyle);
				dataCell = dataRow.createCell(1);
				dataCell.setCellValue("");
				rowNum += 1;
			}
		}
		String xlsxFilePath = Paths.get(outputDirectory, mappedTermsFilename + ".xlsx").toString();
		File mappedTermsXLSX = new File(xlsxFilePath);
		LOGGER.info("Saved XSLX report in '" + xlsxFilePath + "'");
		FileOutputStream outputStream = new FileOutputStream(mappedTermsXLSX.getAbsolutePath());
		workbook.write(outputStream);
		workbook.close();
			
	}

}

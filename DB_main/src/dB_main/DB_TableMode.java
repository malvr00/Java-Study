package dB_main;

import javax.swing.table.AbstractTableModel;

// 공용으로 사용할 Table Data class
public class DB_TableMode extends AbstractTableModel{
	private Object data[][];
	private String columnName[];
	private int nColumn, nRow, nData;
	public DB_TableMode(int column, String header[]) {
		nData = 0;
		nRow = 20;
		nColumn = column;
		columnName = header;
		data = new Object[nRow][nColumn];
	}
  // 열의 수 return 함수
	public int getColumnCount() {
		return nColumn;
	} // getColumnCount end
	
  // 행의 수 return 함수
	public int getRowCount() {
		return nRow;
	} // getRowCount end
	
  // 해당 위치의 data return 함수
	public Object getValueAt(int row, int column) {
		return data[row][column];
	} // getVAlueAt end
	
  // 해당 위치에 data 값 추가 함수
	public void setValueAt(Object value, int row, int column) {
		if(row>=nRow)
			expand(row);
		data[row][column] = value;
	} // setValueAt end
	
  // 현재 크기보다 크기 확장 함수
	public void expand(int row) {
		Object temp[][] = new Object[nRow*2][nColumn];
		for(int i = 0; i<nRow; i++) {
			for(int j=0; j<nColumn; j++) {
				temp[i][j] = data[i][j];
			}
		}
		nRow *= 2;
		data = temp;
	} // expand end
	

	public int getDataCount() {
		return nData;
	} // getDataCount end
	
  // 열(속성) 이름 return 함수 
	public String getColumnName(int col) {
		return columnName[col];
	} // getColumnName end
	
	public boolean isCellEdittable(int row, int col) {
		boolean bool = (col!=0)?true:false;		// 0 번째 열 외 수정 가능하게
		return bool;							// return true : 모두 수정 가능, return false : 모두 수정 불가능
	}	// isCellEdittable end
} // DB_TableMode end

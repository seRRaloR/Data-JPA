package com.jrla.springboot.app.util.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageRender<T> {

	private String url;
	private Page<T> page;
	
	private int numTotalPaginas;
	private int numElemPorPagina;
	private int numPaginaActual;
	private List<PageItem> paginas;
	
	public PageRender(String url, Page<T> page) {
		this.url = url;
		this.page = page;
		this.paginas = new ArrayList<PageItem>();
		
		this.numElemPorPagina = page.getSize();
		this.numTotalPaginas = page.getTotalPages();
		this.numPaginaActual = page.getNumber() + 1;
		
		int pagDesde, pagHasta;
		if (this.numTotalPaginas <= this.numElemPorPagina) {
			pagDesde = 1;
			pagHasta = this.numTotalPaginas;
		} else if (this.numPaginaActual <= this.numElemPorPagina/2) {
			pagDesde = 1;
			pagHasta = this.numElemPorPagina;
		} else if (this.numPaginaActual >= this.numTotalPaginas - this.numElemPorPagina/2) {
			pagDesde = this.numTotalPaginas - this.numElemPorPagina + 1;
			pagHasta = this.numElemPorPagina;
		} else {
			pagDesde = this.numPaginaActual - this.numElemPorPagina/2;
			pagHasta = this.numElemPorPagina;
		}
		
		for (int i=0; i < pagHasta; i++) {
			paginas.add(new PageItem(pagDesde + i, numPaginaActual == pagDesde + i));
		}
	}

	public String getUrl() {
		return url;
	}

	public int getNumTotalPaginas() {
		return numTotalPaginas;
	}

	public int getNumPaginaActual() {
		return numPaginaActual;
	}

	public List<PageItem> getPaginas() {
		return paginas;
	}
	
	public boolean isFirst() {
		return page.isFirst();
	}
	
	public boolean isLast() {
		return page.isLast();
	}

	public boolean isHasNext() {
		return page.hasNext();
	}

	public boolean isHasPrevious() {
		return page.hasPrevious();
	}
}

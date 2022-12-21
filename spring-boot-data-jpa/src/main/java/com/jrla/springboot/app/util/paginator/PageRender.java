package com.jrla.springboot.app.util.paginator;

import org.springframework.data.domain.Page;

public class PageRender<T> {

	private String url;
	private Page<T> page;
	
	private int numTotalPaginas;
	private int numElemPorPagina;
	private int numPaginaActual;
	
	public PageRender(String url, Page<T> page) {
		this.url = url;
		this.page = page;
		
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
	}
	
	
}

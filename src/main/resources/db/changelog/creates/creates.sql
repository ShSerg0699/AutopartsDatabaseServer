CREATE TABLE public.buyer
(
    buyerid integer NOT NULL DEFAULT nextval('buyer_buyerid_seq'::regclass),
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT buyer_pkey PRIMARY KEY (buyerid)
)

TABLESPACE pg_default;

ALTER TABLE public.buyer
    OWNER to postgres;

CREATE TABLE public.cell_in_stock
(
    cellid integer NOT NULL DEFAULT nextval('cell_in_stock_cellid_seq'::regclass),
    space integer NOT NULL,
    CONSTRAINT cell_in_stock_pkey PRIMARY KEY (cellid)
)

TABLESPACE pg_default;

ALTER TABLE public.cell_in_stock
    OWNER to postgres;

CREATE TABLE public.detail
(
    detailid integer NOT NULL DEFAULT nextval('detail_detailid_seq'::regclass),
    name character varying(255) COLLATE pg_catalog."default",
    price integer NOT NULL,
    size integer NOT NULL,
    CONSTRAINT detail_pkey PRIMARY KEY (detailid)
)

TABLESPACE pg_default;

ALTER TABLE public.detail
    OWNER to postgres;

CREATE TABLE public.cell_detail
(
    cell_detailid integer NOT NULL DEFAULT nextval('cell_detail_cell_detailid_seq'::regclass),
    appearance_date date,
    quantity integer NOT NULL,
    cell_in_stock_cellid integer,
    detail_detailid integer,
    CONSTRAINT cell_detail_pkey PRIMARY KEY (cell_detailid),
    CONSTRAINT fkhof3s765p8ebsfckg35nsdnu2 FOREIGN KEY (detail_detailid)
        REFERENCES public.detail (detailid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkojlbqohswubadoorrn5lfwh7p FOREIGN KEY (cell_in_stock_cellid)
        REFERENCES public.cell_in_stock (cellid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.cell_detail
    OWNER to postgres;

CREATE TABLE public.purchase
(
    purchaseid integer NOT NULL DEFAULT nextval('purchase_purchaseid_seq'::regclass),
    purchase_date date,
    buyer_buyerid integer,
    CONSTRAINT purchase_pkey PRIMARY KEY (purchaseid),
    CONSTRAINT fkshq37r75rwbvruyxieuje27q6 FOREIGN KEY (buyer_buyerid)
        REFERENCES public.buyer (buyerid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.purchase
    OWNER to postgres;

CREATE TABLE public.purchase_detail
(
    purchase_detailid integer NOT NULL DEFAULT nextval('purchase_detail_purchase_detailid_seq'::regclass),
    quantity integer NOT NULL,
    detail_detailid integer,
    purchase_purchaseid integer,
    CONSTRAINT purchase_detail_pkey PRIMARY KEY (purchase_detailid),
    CONSTRAINT fkbngjt88we7w2faa8smje9k8jx FOREIGN KEY (detail_detailid)
        REFERENCES public.detail (detailid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkkre2gyf5xhwj0gpdmmbmgoyug FOREIGN KEY (purchase_purchaseid)
        REFERENCES public.purchase (purchaseid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.purchase_detail
    OWNER to postgres;

CREATE TABLE public.supplier
(
    supplierid integer NOT NULL DEFAULT nextval('supplier_supplierid_seq'::regclass),
    contract bigint,
    delivery_time character varying(255) COLLATE pg_catalog."default",
    discount integer,
    guarantee character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    type integer NOT NULL,
    CONSTRAINT supplier_pkey PRIMARY KEY (supplierid)
)

TABLESPACE pg_default;

ALTER TABLE public.supplier
    OWNER to postgres;

CREATE TABLE public.price_list
(
    price_listid integer NOT NULL DEFAULT nextval('price_list_price_listid_seq'::regclass),
    price integer NOT NULL,
    detail_detailid integer,
    supplier_supplierid integer,
    CONSTRAINT price_list_pkey PRIMARY KEY (price_listid),
    CONSTRAINT fk3a67n7fp5jhgketl4qiovqkpl FOREIGN KEY (detail_detailid)
        REFERENCES public.detail (detailid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkb9aaalfcclbbue88u4ny8yb7n FOREIGN KEY (supplier_supplierid)
        REFERENCES public.supplier (supplierid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.price_list
    OWNER to postgres;

CREATE TABLE public.supply
(
    supplyid integer NOT NULL DEFAULT nextval('supply_supplyid_seq'::regclass),
    customs_clearance integer NOT NULL,
    delivery_date date,
    marriage_rate integer NOT NULL,
    supplier_supplierid integer,
    CONSTRAINT supply_pkey PRIMARY KEY (supplyid),
    CONSTRAINT fkhbngk4dd55b0r9lod98ivtlfu FOREIGN KEY (supplier_supplierid)
        REFERENCES public.supplier (supplierid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.supply
    OWNER to postgres;

CREATE TABLE public.supply_detail
(
    supply_detailid integer NOT NULL DEFAULT nextval('supply_detail_supply_detailid_seq'::regclass),
    quantity integer NOT NULL,
    detail_detailid integer,
    supply_supplyid integer,
    CONSTRAINT supply_detail_pkey PRIMARY KEY (supply_detailid),
    CONSTRAINT fki3axv9p3ckc1ongo7wshafd7r FOREIGN KEY (supply_supplyid)
        REFERENCES public.supply (supplyid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkr44lhabqimi4f2h1xevhl0p0e FOREIGN KEY (detail_detailid)
        REFERENCES public.detail (detailid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.supply_detail
    OWNER to postgres;
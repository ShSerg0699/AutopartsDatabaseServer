CREATE TABLE public.buyer
(
    buyerid integer primary key generated always as identity,
    name character varying(255)
);


CREATE TABLE public.cell_in_stock
(
    cellid integer primary key generated always as identity,
    space integer NOT NULL
);

CREATE TABLE public.detail
(
    detailid integer primary key generated always as identity,
    name character varying(255),
    price integer NOT NULL,
    size integer NOT NULL
);

CREATE TABLE public.cell_detail
(
    cell_detailid integer primary key generated always as identity,
    appearance_date date,
    quantity integer NOT NULL,
    cell_in_stock_cellid integer references cell_in_stock,
    detail_detailid integer references detail
);

CREATE TABLE public.purchase
(
    purchaseid integer primary key generated always as identity,
    purchase_date date,
    buyer_buyerid integer references buyer
);

CREATE TABLE public.purchase_detail
(
    purchase_detailid integer primary key generated always as identity,
    quantity integer NOT NULL,
    detail_detailid integer references detail,
    purchase_purchaseid integer references purchase
);

CREATE TABLE public.supplier
(
    supplierid integer primary key generated always as identity,
    contract bigint NULL,
    delivery_time character varying(255),
    discount integer NULL,
    guarantee character varying(255) NULL,
    name character varying(255),
    type integer NOT NULL
);

CREATE TABLE public.price_list
(
    price_listid integer primary key generated always as identity,
    price integer NOT NULL,
    detail_detailid integer references detail,
    supplier_supplierid integer references supplier
);

CREATE TABLE public.supply
(
    supplyid integer primary key generated always as identity,
    customs_clearance integer NOT NULL,
    delivery_date date,
    marriage_rate integer NOT NULL,
    supplier_supplierid integer references supplier
);

CREATE TABLE public.supply_detail
(
    supply_detailid integer primary key generated always as identity,
    quantity integer NOT NULL,
    detail_detailid integer references  detail,
    supply_supplyid integer references supply
);
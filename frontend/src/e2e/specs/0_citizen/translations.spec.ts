// SPDX-FileCopyrightText: 2021 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

import { translations } from 'lib-customizations/citizen'
import React from 'react'
import { isArray, toArray, isObject } from 'lodash'

const checkOuluTranslation = (value: string, errors: string[]) => {
    if (value !== null){
        if (value.toLowerCase().includes('espoo') || value.toLowerCase().includes('tampere')) {
            console.error(value)
            errors.push(value);
        }
    }
}

const checkOuluTranslationInner = (object: any, errors: string[]) => {
    if (React.isValidElement(object)) {
        checkOuluTranslation(JSON.stringify(object), errors);
    } else if (isObject(object)) {
        Object.keys(object).forEach(key => {
            checkOuluTranslationInner(object[key], errors)
        });
    } else if (isArray(object)) {
        let valAsArray = toArray(object)
        for (let val in valAsArray) {
            checkOuluTranslationInner(val, errors)
        }
    } else {
        checkOuluTranslation(object, errors)
    }
}

const checkOuluTranslations = (translationsFi: { [key: string]: any }): string[] => {
    let errors: string[] = []
    for (const key of Object.keys(translationsFi)) {
        checkOuluTranslationInner(translationsFi[key], errors)
    }
    return errors
}

describe('Citizen translations', () => {
    test('fi', async () => {
        const errors = checkOuluTranslations(translations.fi)
        expect(errors).toEqual([])
    })
    test('en', async () => {
        const errors = checkOuluTranslations(translations.en)
        expect(errors).toEqual([])
    })
})



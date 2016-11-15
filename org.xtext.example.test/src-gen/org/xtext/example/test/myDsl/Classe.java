/**
 * generated by Xtext 2.10.0
 */
package org.xtext.example.test.myDsl;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Classe</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.xtext.example.test.myDsl.Classe#getName <em>Name</em>}</li>
 *   <li>{@link org.xtext.example.test.myDsl.Classe#getAttributs <em>Attributs</em>}</li>
 * </ul>
 *
 * @see org.xtext.example.test.myDsl.MyDslPackage#getClasse()
 * @model
 * @generated
 */
public interface Classe extends EObject
{
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see org.xtext.example.test.myDsl.MyDslPackage#getClasse_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link org.xtext.example.test.myDsl.Classe#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Attributs</b></em>' containment reference list.
   * The list contents are of type {@link org.xtext.example.test.myDsl.Attribut}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Attributs</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Attributs</em>' containment reference list.
   * @see org.xtext.example.test.myDsl.MyDslPackage#getClasse_Attributs()
   * @model containment="true"
   * @generated
   */
  EList<Attribut> getAttributs();

} // Classe